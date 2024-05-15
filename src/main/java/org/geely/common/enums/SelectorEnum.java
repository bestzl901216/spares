package org.geely.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author cong huang
 */
@Getter
@AllArgsConstructor
public enum SelectorEnum {
    PRODUCT_BRAND(1, "品牌"),
    PRODUCT_QUALITY(2, "品质"),
    PRODUCT_TAG(3, "标签"),
    SKU_SPECS_TYPE(4, "SKU规格类型"),
    SKU_SPECS(5, "SKU规格"),
    EXPRESS_COMPANY(6, "快递公司");
    @EnumValue
    private final Integer id;
    private final String name;
}
