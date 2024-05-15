package org.geely.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author cong huang
 */
@Getter
@AllArgsConstructor
public enum CartSkuEnum {
    INVALID(-1, "已失效"),
    NORMAL(0, "正常"),
    OFF(1, "已下架");

    @EnumValue
    private final Integer id;
    private final String name;
}
