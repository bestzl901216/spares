package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 物料店铺关联
 *
 * @author cong huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "material_shop_rel", autoResultMap = true)
public class MaterialShopRelDO extends BaseDO {
    private Integer materialId;
    private Integer shopId;
}
