package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品属性
 *
 * @author cong huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "product_props", autoResultMap = true)
public class ProductPropsDO extends BaseDO {
    private Integer productId;
    private String name;
    private String value;
    private Integer sort;
}
