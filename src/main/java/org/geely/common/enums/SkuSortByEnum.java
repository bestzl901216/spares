package org.geely.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author cong huang
 */
@Getter
@AllArgsConstructor
public enum SkuSortByEnum {
    DEFAULT(0, "默认"),
    SALES_VOLUME(1, "销量"),
    PRICE(2, "价格");

    @EnumValue
    private final Integer id;
    private final String name;
}
