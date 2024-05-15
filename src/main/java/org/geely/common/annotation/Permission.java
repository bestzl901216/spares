package org.geely.common.annotation;

import java.lang.annotation.*;

/**
 * 权限控制
 *
 * @author yancheng.guo
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Permission {
    String[] value() default {};
}
