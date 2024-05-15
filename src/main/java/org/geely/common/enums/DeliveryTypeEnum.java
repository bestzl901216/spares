package org.geely.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author cong huang
 */
@Getter
@AllArgsConstructor
public enum DeliveryTypeEnum {
    EMPTY(0, "无"),
    MANUFACTURER(1, "厂家直发");

    @EnumValue
    private final Integer id;
    private final String name;

    public static DeliveryTypeEnum of(Integer id) {
        for (DeliveryTypeEnum value : values()) {
            if (value.id.equals(id)) {
                return value;
            }
        }
        return EMPTY;
    }
}
