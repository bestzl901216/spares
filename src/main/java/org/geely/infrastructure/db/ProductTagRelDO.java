package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品标签关联
 *
 * @author cong huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "product_tag_rel", autoResultMap = true)
public class ProductTagRelDO extends BaseDO {
    private Integer productId;
    private Integer tagId;
}
