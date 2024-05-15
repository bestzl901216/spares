package org.geely.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ricardo zhou
 */
@Getter
@AllArgsConstructor
public enum PlatformTypeEnum {
    CUSTOMER(0, "客户"),
    PLATFORM(1, "平台"),
    MALL(2, "租户"),
    SUPPLIER(3, "商家"),
    SHOP(4, "店铺");

    @EnumValue
    private final Integer id;
    private final String name;
}
