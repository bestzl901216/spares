package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品详情图片
 *
 * @author cong huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "product_image", autoResultMap = true)
public class ProductImageDO extends BaseDO {
    private Integer productId;
    private String url       ;
    private Integer sort      ;
}
