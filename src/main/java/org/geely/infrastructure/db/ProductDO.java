package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.geely.common.enums.ProductStateEnum;

/**
 * 商品
 * @author cong huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "product", autoResultMap = true)
public class ProductDO extends BaseDO {
    private Integer mallId;
    private Integer supplierId;
    private Integer shopId;
    private String code;
    private Integer categoryId;
    private Integer qualityId;
    private Integer brandId;
    private String name;
    private String description;
    private ProductStateEnum state;
    private Integer sort;
}
