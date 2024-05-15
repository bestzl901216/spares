package org.geely.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author cong huang
 */
@Getter
@AllArgsConstructor
public enum PayTypeEnum {
    EMPTY(0, ""),
    ALI_PAY(1, "支付宝"),
    WECHAT(2, "微信"),
    ENTERPRISE_ONLINE(3, "企业网银"),
    ENTERPRISE_OFFLINE(4, "线下付款"),
    CREDIT(5, "挂账"),
    BALANCE(6, "余额");

    @EnumValue
    private final Integer id;
    private final String name;

    public static PayTypeEnum of(Integer id) {
        for (PayTypeEnum value : values()) {
            if (value.id.equals(id)) {
                return value;
            }
        }
        return EMPTY;
    }
}
