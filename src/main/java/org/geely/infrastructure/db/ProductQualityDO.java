package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 商品品质
 *
 * @author cong huang
 */
@Data
@TableName(value = "product_quality", autoResultMap = true)
public class ProductQualityDO extends BaseDO {
    private Integer mallId;
    private String name;
    private String remark;
    private Integer sort;
}
