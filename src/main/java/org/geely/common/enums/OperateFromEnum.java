package org.geely.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author cong huang
 */
@Getter
@AllArgsConstructor
public enum OperateFromEnum {
    EMPTY(0, "无"),
    SYSTEM(1, "系统"),
    BUYER(2, "买家"),
    SELLER(3, "卖家");

    @EnumValue
    private final Integer id;
    private final String name;
}
