package org.geely.common.interceptor;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.geely.common.annotation.NoLogin;
import org.geely.common.annotation.NoPlatform;
import org.geely.common.annotation.Permission;
import org.geely.common.constants.AuthConstant;
import org.geely.common.constants.MvcConstant;
import org.geely.common.enums.PlatformTypeEnum;
import org.geely.common.enums.ResponseEnum;
import org.geely.common.model.ResultJson;
import org.geely.common.model.UserDetailDTO;
import org.geely.common.session.SessionContext;
import org.geely.common.utils.OperatorUtil;
import org.geely.domain.common.Account;
import org.geely.domain.common.data.SessionData;
import org.geely.domain.support.Role;
import org.geely.domain.support.data.RoleData;
import org.geely.infrastructure.redis.RedisGateway;
import org.geely.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.geely.common.enums.ResponseEnum.USER_NOT_LOGIN;


/**
 * 账号信息拦截器
 *
 * @author ricardo zhou
 */
@Slf4j
public class AccountInterceptor implements HandlerInterceptor {

    @Resource
    private UserService userService;
    @Resource
    private RedisGateway redisGateway;
    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        if (method.equalsIgnoreCase("OPTIONS")) {
            return true;
        }
        //未登录
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //判断方法或者类上是否有免登录注解
            if (handlerMethod.getMethod().isAnnotationPresent(NoLogin.class) || handlerMethod.getBeanType().isAnnotationPresent(NoLogin.class)) {
                return true;
            }
        } else if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        Optional<SessionData> sessionDataOptional = querySessionDataFromRedis(request);
        if (Boolean.FALSE.equals(sessionDataOptional.isPresent())) {
            responseNotLogin(USER_NOT_LOGIN, response);
            return false;
        }

        assert handler instanceof HandlerMethod;
        HandlerMethod hm = (HandlerMethod) handler;
        if (hm.getMethod().isAnnotationPresent(NoPlatform.class) || hm.getBeanType().isAnnotationPresent(NoPlatform.class)) {
            return true;
        }
        // 通过header指定平台类型和平台id
        PlatformTypeEnum platformType;
        int platformId;
        try {
            platformType = PlatformTypeEnum.valueOf(request.getHeader(MvcConstant.PLATFORM_HEADER).toUpperCase());
            platformId = Integer.parseInt(request.getHeader(MvcConstant.PLATFORM_ID_HEADER));
        } catch (Exception e) {
            responseNoPlatform(response);
            return false;
        }
        Optional<RoleData> roleDataOpt = sessionDataOptional.map(SessionData::getRoleDataSet).orElseGet(Sets::newHashSet)
                .stream().filter(roleData -> roleData.getPlatformType() == platformType && roleData.getPlatformId() == platformId).findFirst();
        if (!roleDataOpt.isPresent()) {
            responseNoRole(response);
            return false;
        }
        // 校验权限
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //判断方法或者类上是否有免登录注解
            if (handlerMethod.getMethod().isAnnotationPresent(Permission.class) || handlerMethod.getBeanType().isAnnotationPresent(Permission.class)) {
                Permission permission = handlerMethod.getMethod().getAnnotation(Permission.class);
                List<String> allowedPermissions = Lists.newArrayList(permission.value());
                boolean hasPermission = roleDataOpt.get().getPermissions().stream().anyMatch(allowedPermissions::contains);
                if (hasPermission) {
                    return true;
                } else {
                    this.responseNoPermission(response);
                    return false;
                }
            }
        }
        String mallIdHeader = request.getHeader(MvcConstant.MALL_ID);
        Integer mallId = StringUtils.isBlank(mallIdHeader) ? 0 : Integer.parseInt(mallIdHeader);
        OperatorUtil.setAccountData(sessionDataOptional.map(SessionData::getAccountData).orElse(null));
        OperatorUtil.setRoleData(roleDataOpt.get());
        OperatorUtil.setMallData(sessionDataOptional.map(SessionData::getMallDataSet).orElseGet(Sets::newHashSet).stream()
                .filter(mallData -> mallData.getId().equals(mallId)).findFirst().orElse(null));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // do nothing
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        OperatorUtil.clear();
    }

    private Optional<SessionData> querySessionDataFromRedis(HttpServletRequest request) {
        String token = request.getHeader(AuthConstant.TOKEN_KEY);
        Optional<SessionData> sessionDataOptional = redisGateway.querySessionData(token);
        // 管理员通过sso统一登录后，设置对应的session信息
        if (!sessionDataOptional.isPresent() && SessionContext.getIdaasUser() != null) {
            // 根据iddasuser获取账号信息
            UserDetailDTO userDetail = userService.getUserDetail(SessionContext.getIdaasUser().getSub());
            Assert.notNull(userDetail, "用户不存在");
            Account account = Account.instanceOfPhone(userDetail.getPhoneNumber());
            SessionData sessionData = new SessionData();
            sessionData.setAccountData(account.getData());
            sessionData.setRoleDataSet(account.roles().stream().map(Role::getData).collect(Collectors.toSet()));
            redisGateway.saveSessionData(token, sessionData);
            sessionDataOptional = Optional.of(sessionData);
        }
        return sessionDataOptional;
    }

    /**
     * json返回错误提示
     *
     * @param httpResponse http响应
     * @throws IOException io异常
     */
    private void responseNoPermission(HttpServletResponse httpResponse) throws IOException {
        ResultJson<String> response = ResultJson.fail("no permission", "无权限访问");
        String output = JSON.toJSONString(response);
        httpResponse.setContentType(AuthConstant.JSON_CONTENT_TYPE);
        httpResponse.setStatus(HttpServletResponse.SC_OK);
        httpResponse.getWriter().println(output);
    }

    /**
     * json返回错误提示
     *
     * @param httpResponse http相应
     * @throws IOException io异常
     */
    private void responseNoPlatform(HttpServletResponse httpResponse) throws IOException {
        ResultJson<String> response = ResultJson.fail("no platform", "未指定平台");
        String output = JSON.toJSONString(response);
        httpResponse.setContentType(AuthConstant.JSON_CONTENT_TYPE);
        httpResponse.setStatus(HttpServletResponse.SC_OK);
        httpResponse.getWriter().println(output);
    }

    /**
     * json返回错误提示
     *
     * @param httpResponse http相应
     * @throws IOException io异常
     */
    private void responseNoRole(HttpServletResponse httpResponse) throws IOException {
        ResultJson<String> response = ResultJson.fail("no role", "非平台用户");
        String output = JSON.toJSONString(response);
        httpResponse.setContentType(AuthConstant.JSON_CONTENT_TYPE);
        httpResponse.setStatus(HttpServletResponse.SC_OK);
        httpResponse.getWriter().println(output);
    }

    private void responseNotLogin(ResponseEnum responseEnum, HttpServletResponse httpResponse) throws IOException {
        ResultJson<String> response = new ResultJson<>(responseEnum.getCode(), responseEnum.getMessage(), "");
        String output = JSON.toJSONString(response);
        httpResponse.setContentType(AuthConstant.JSON_CONTENT_TYPE);
        httpResponse.setStatus(HttpServletResponse.SC_OK);
        httpResponse.getWriter().println(output);
    }

}
