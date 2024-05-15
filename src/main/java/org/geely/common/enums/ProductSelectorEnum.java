package org.geely.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author cong huang
 */
@Getter
@AllArgsConstructor
public enum ProductSelectorEnum {
    BRAND(1, "品牌"),
    QUALITY(2, "品质"),
    TAG(3, "标签");
    @EnumValue
    private final Integer id;
    private final String name;
}
