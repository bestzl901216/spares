package org.geely.domain.core.data;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 67091
 */
@Data
public class SkuPriceData implements Serializable {
    private Integer id;
    private Integer skuId;
    private Integer marketChannelId;
    private BigDecimal price;
    private Integer version;
    public  SkuPriceData() {

    }
    public SkuPriceData(Integer skuId, Integer marketChannelId, BigDecimal price) {
        this.skuId = skuId;
        this.marketChannelId = marketChannelId;
        this.price = price;
        this.version = 0;
    }
}
