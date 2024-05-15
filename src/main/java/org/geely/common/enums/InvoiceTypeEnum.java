package org.geely.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 67091
 */
@Getter
@AllArgsConstructor
public enum InvoiceTypeEnum {
    /**
     * 个人
     */
    PERSONAL(0, "个人"),
    /**
     * 企业
     */
    ENTERPRISE(1, "企业");
    @EnumValue
    private final Integer id;
    private final String name;
}
