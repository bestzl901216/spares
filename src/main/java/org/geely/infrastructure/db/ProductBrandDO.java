package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品品牌
 *
 * @author cong huang
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "product_brand", autoResultMap = true)
public class ProductBrandDO extends BaseDO {
    private Integer mallId;
    private String name;
    private Integer sort;
    private String logo;
}
