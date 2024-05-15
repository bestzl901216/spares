package org.geely.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ricaro zhou
 */
@Getter
@AllArgsConstructor
public enum EnableStateEnum {
    DISABLE(0, "禁用"),
    ENABLE(1, "启用");
    @EnumValue
    private final Integer id;
    private final String name;
}
