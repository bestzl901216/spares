package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * sku规格关联
 *
 * @author cong huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sku_specs_rel", autoResultMap = true)
public class SkuSpecsRelDO extends BaseDO {
    private Integer skuId;
    private Integer specsTypeId;
    private String specs;
}
