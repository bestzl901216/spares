package org.geely.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.geely.gsapi.GSAPI;
import org.apache.logging.log4j.util.Strings;
import org.geely.common.annotation.NoLogin;
import org.geely.common.constants.AuthConstant;
import org.geely.common.enums.ResponseEnum;
import org.geely.common.model.IdaasUser;
import org.geely.common.model.ResultJson;
import org.geely.common.session.SessionContext;
import org.geely.common.session.SessionInfo;
import org.geely.service.LoginSerivice;
import org.geely.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.geely.common.enums.ResponseEnum.USER_NOT_LOGIN;
import static org.geely.common.enums.ResponseEnum.USER_TOKEN_EXPIRED;

/**
 * 会话校验统一拦截器
 *
 * @author yancheng.guo
 */

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthInterceptor.class);

    /**
     * 白名单接口列表（健康检查）
     */
    private static final String[] WHITE_LIST = {"/health"};

    @Resource
    private TokenService tokenService;
    @Resource
    private LoginSerivice loginSerivice;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //白名单校验
        String method = request.getMethod();
        if (method.equalsIgnoreCase("OPTIONS")) {
            return true;
        }
        for (String whiteResource : WHITE_LIST) {
            if (Strings.isBlank(whiteResource)) {
                continue;
            }
            if (whiteResource.contains("%")) {
                if (request.getRequestURI().contains(whiteResource.substring(0, whiteResource.lastIndexOf('%')))) {
                    return true;
                }
            } else if (whiteResource.contains("**")) {
                if (request.getRequestURI().startsWith(whiteResource.substring(0, whiteResource.lastIndexOf('*') - 1))) {
                    return true;
                }
            } else {
                if (request.getRequestURI().equals(whiteResource)) {
                    return true;
                }
            }
        }

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //判断方法或者类上是否有免登录注解
            if (handlerMethod.getMethod().isAnnotationPresent(NoLogin.class) || handlerMethod.getBeanType().isAnnotationPresent(NoLogin.class)) {
                return true;
            }
        } else if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        String token = request.getHeader(AuthConstant.TOKEN_KEY);
        //未登录
        if (Strings.isBlank(token)) {
            //重定向到sso登录
            responseNotLogin(USER_NOT_LOGIN, response);
            return false;
        }
        token= GSAPI.getSafeDirInstance().verifyNonNullString(token);
        //token已失效
        IdaasUser idaasUser = tokenService.getUserInfo(token);

        if (null == idaasUser) {
            //重定向到sso登录
            responseNotLogin(USER_TOKEN_EXPIRED, response);
            return false;
        }

        SessionInfo sessionInfo = new SessionInfo(token, idaasUser);
        SessionContext.setSessionInfo(sessionInfo);
        LOGGER.info("setSession{}", sessionInfo);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // do nothing
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SessionContext.removeSessionInfo();
    }

    /**
     * json返回错误提示
     *
     * @param responseEnum 错误枚举
     * @param httpResponse http响应
     * @throws IOException IO异常
     */
    private void responseNotLogin(ResponseEnum responseEnum, HttpServletResponse httpResponse) throws IOException {
        String loginUrl = loginSerivice.createLoginRedirectUrl();
        ResultJson<String> response = new ResultJson<>(responseEnum.getCode(), responseEnum.getMessage(), loginUrl);
        String output = JSON.toJSONString(response);
        httpResponse.setContentType(AuthConstant.JSON_CONTENT_TYPE);
        httpResponse.setStatus(HttpServletResponse.SC_OK);
        httpResponse.getWriter().println(output);
    }

}
