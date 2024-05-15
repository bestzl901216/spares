package org.geely.common.annotation;

import java.lang.annotation.*;

/**
 * header中免传platform信息
 * @author cong huang
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface NoPlatform {

}
