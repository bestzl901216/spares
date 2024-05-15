package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * sku渠道固定价格
 *
 * @author cong huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sku_price", autoResultMap = true)
public class SkuPriceDO extends BaseDO {
    private Integer skuId;
    private Integer marketChannelId;
    private BigDecimal price;
}
