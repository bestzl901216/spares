package org.geely.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ricaro zhou
 */
@Getter
@AllArgsConstructor
public enum SnPrefixEnum {
    SO(0, "订单"),
    OP(1, "支付单"),
    XX(2, "线下支付流水"),
    EX(3, "发货单");
    @EnumValue
    private final Integer id;
    private final String name;
}
