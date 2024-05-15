package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * sku规格
 *
 * @author cong huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sku_specs", autoResultMap = true)
public class SkuSpecsDO extends BaseDO {
    private Integer typeId;
    private String name;
    private Integer sort;
    private Boolean selectable;
}
