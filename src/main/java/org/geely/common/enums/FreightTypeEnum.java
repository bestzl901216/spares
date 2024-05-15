package org.geely.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author cong huang
 */
@Getter
@AllArgsConstructor
public enum FreightTypeEnum {
    FREE_SHIPPING(1, "包邮"),
    COLLECT(2, "到付");

    @EnumValue
    private final Integer id;
    private final String name;

    public static FreightTypeEnum of(Integer id) {
        for (FreightTypeEnum value : values()) {
            if (value.id.equals(id)) {
                return value;
            }
        }
        return null;
    }
}
