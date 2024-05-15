package org.geely.common.annotation;

import java.lang.annotation.*;

/**
 * 免登录校验注解
 * @author yancheng.guo
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface NoLogin {

}
