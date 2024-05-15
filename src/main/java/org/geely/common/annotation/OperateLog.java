package org.geely.common.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface OperateLog {

    /**
     * 所属模块：如用户管理
     * @return
     */
    String module() default "";

    /**
     * 操作名称：如用户删除
     * @return
     */
    String operateName() default "";

    /**
     * 类型：@See OperateType
     * @return
     */
    String type() default "";

}
