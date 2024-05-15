package org.geely.common.annotation;

import org.mapstruct.Mapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author ricardo zhou
 */
@Retention(RetentionPolicy.CLASS)
@Mapping(target = "creator", ignore = true)
@Mapping(target = "createTime", ignore = true)
@Mapping(target = "updater", ignore = true)
@Mapping(target = "updateTime", ignore = true)
@Mapping(target = "deletedFlag", ignore = true)
public @interface DataToDoIgnore {
}
