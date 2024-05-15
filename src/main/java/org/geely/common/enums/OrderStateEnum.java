package org.geely.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author cong huang
 */
@Getter
@AllArgsConstructor
public enum OrderStateEnum {
    REFUNDED(-2, "已退款"),
    CANCELED(-1, "已取消"),
    UNKNOWN(0, "未知"),
    TO_BE_PAID(1, "待支付"),
    TO_BE_APPROVED(2, "待审批"),
    TO_BE_SYNC(3, "待同步"),
    SYNC_FAILED(4, "同步失败"),
    TO_BE_SHIPPED(5, "待发货"),
    TO_BE_RECEIVED(6, "待收货"),
    RECEIVED(7, "已收货"),
    FINISHED(8, "已完成");

    @EnumValue
    private final Integer id;
    private final String name;

    public static OrderStateEnum valueOf(Integer id) {
        for (OrderStateEnum state : OrderStateEnum.values()) {
            if (state.id.equals(id)) {
                return state;
            }
        }
        return UNKNOWN;
    }
}
