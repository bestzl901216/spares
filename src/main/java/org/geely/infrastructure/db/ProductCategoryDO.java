package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 前台商品类目
 *
 * @author cong huang
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "product_category", autoResultMap = true)
public class ProductCategoryDO extends BaseDO {
    private Integer mallId;
    private Integer parentId;
    private String parentPath;
    private String name;
    private Integer sort;
    private Integer level;
}
