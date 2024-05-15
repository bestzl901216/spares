package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * sku图片
 *
 * @author cong huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sku_image", autoResultMap = true)
public class SkuImageDO extends BaseDO {
    private Integer skuId;
    private String url;
    private Integer sort;
}
