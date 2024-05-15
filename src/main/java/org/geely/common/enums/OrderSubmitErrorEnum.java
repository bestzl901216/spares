package org.geely.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author cong huang
 */
@Getter
@AllArgsConstructor
public enum OrderSubmitErrorEnum {
    UNKNOWN(0, ""),
    SKU_INVALID(1, "部分商品已失效，是否清除？"),
    STOCK_CHANGED(2, "商品库存变化，请重新提交"),
    PRICE_CHANGED(3, "商品价格变化，请重新提交");

    @EnumValue
    private final Integer id;
    private final String message;

    public static OrderSubmitErrorEnum of(String message) {
        for (OrderSubmitErrorEnum state : OrderSubmitErrorEnum.values()) {
            if (state.message.equals(message)) {
                return state;
            }
        }
        return UNKNOWN;
    }
}
