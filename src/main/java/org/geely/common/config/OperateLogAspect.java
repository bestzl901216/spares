package org.geely.common.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.geely.common.model.IdaasUser;
import org.geely.common.model.OperateLogDTO;
import org.geely.common.runnable.OperateLogSaveTask;
import org.geely.common.session.SessionContext;
import org.geely.common.utils.ThreadPoolUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Aspect
@Component
@Slf4j
public class OperateLogAspect {

    @Value("${baseService.host}")
    private String host;

    @Value("${baseService.appId}")
    private String appId;

    /**
     * 以自定义 @OperateLog 注解为切点
     */
    @Pointcut("@annotation(org.geely.common.annotation.OperateLog)")
    public void operateLog() {
    }

    /**
     * 在切点之前织入
     */
    @Before("operateLog()")
    public void doBefore(JoinPoint joinPoint) {
        // do nothing
    }

    /**
     * 在切点之后织入
     */
    @After("operateLog()")
    public void doAfter() {
        // do nothing
    }

    /**
     * 环绕
     */
    @Around("operateLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = proceedingJoinPoint.proceed();

        OperateLogDTO operateLogDTO = getAspectOperateLog(proceedingJoinPoint, "success", result);
        ThreadPoolUtils.OPERATE_LOG_THREAD_POOL.execute(new OperateLogSaveTask(operateLogDTO, host));

        return result;
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "operateLog()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) throws NoSuchMethodException {
        OperateLogDTO operateLogDTO = getAspectOperateLog(joinPoint, "fail", null);
        ThreadPoolUtils.OPERATE_LOG_THREAD_POOL.execute(new OperateLogSaveTask(operateLogDTO, host));
    }


    /**
     * 获取切面注解的描述-正确方式
     *
     * @param joinPoint 切点
     * @return 描述信息
     */
    public OperateLogDTO getAspectOperateLog(JoinPoint joinPoint, String result, Object responseObj)
            throws NoSuchMethodException {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = null;
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        methodSignature = (MethodSignature) signature;
        Method currentMethod = joinPoint.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());

        OperateLogDTO operateLogDTO = new OperateLogDTO();
        IdaasUser idaasUser = SessionContext.getIdaasUser();
        if (null != idaasUser) {
            operateLogDTO.setUsername(idaasUser.getUsername());
            operateLogDTO.setDisplayName(idaasUser.getNickname());
        }
        operateLogDTO.setAppId(appId);
        operateLogDTO.setModule(currentMethod.getAnnotation(org.geely.common.annotation.OperateLog.class).module());
        operateLogDTO.setOperateName(currentMethod.getAnnotation(org.geely.common.annotation.OperateLog.class).operateName());
        operateLogDTO.setType(currentMethod.getAnnotation(org.geely.common.annotation.OperateLog.class).type());
        operateLogDTO.setCreateTime(new Date());
        operateLogDTO.setResult(result);
        Object args = joinPoint.getArgs();
        if (!Objects.isNull(args)) {
            String params = argsArrayToString(joinPoint.getArgs());
            operateLogDTO.setRequestBody(StringUtils.substring(params, 0, 2000));
        }
        if (!Objects.isNull(responseObj)) {
            operateLogDTO.setResponseBody(StringUtils.substring(JSON.toJSONString(responseObj), 0, 2000));
        }

        return operateLogDTO;
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        StringBuilder params = new StringBuilder();
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (!Objects.isNull(o) && !isFilterObject(o)) {
                    String jsonObj = JSON.toJSONString(o);
                    params.append(jsonObj);
                }
            }
        }
        return params.toString().trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }

}
