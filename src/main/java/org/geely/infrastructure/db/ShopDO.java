package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.geely.common.enums.EnableStateEnum;

/**
 * 店铺
 *
 * @author cong huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "shop", autoResultMap = true)
public class ShopDO extends BaseDO {
    private Integer mallId;
    private Integer supplierId;
    private String name;
    private EnableStateEnum state;
}
