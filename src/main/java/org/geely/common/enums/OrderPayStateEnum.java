package org.geely.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author cong huang
 */
@Getter
@AllArgsConstructor
public enum OrderPayStateEnum {
    FAIL(-1, "失败"),
    TO_BE_PAID(0, "待支付"),
    PENDING_APPROVAL(1, "待审核"),
    SUCCESS(2, "成功"),
    PAYING(3, "支付中");
    @EnumValue
    private final Integer id;
    private final String name;
}
