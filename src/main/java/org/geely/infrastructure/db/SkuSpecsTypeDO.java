package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * sku规格类型
 *
 * @author cong huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sku_specs_type", autoResultMap = true)
public class SkuSpecsTypeDO extends BaseDO {
    private Integer mallId;
    private String name;
    private Integer sort;
}
