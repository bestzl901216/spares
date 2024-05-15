package org.geely.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author cong huang
 */
@Getter
@AllArgsConstructor
public enum ProductStateEnum {
    AUDIT_FAILURE(-1, "审核失败"), PENDING(0, "待审核"), OFF(1, "已下架"), ON(2, "已上架");
    @EnumValue
    private final Integer id;
    private final String name;
}
