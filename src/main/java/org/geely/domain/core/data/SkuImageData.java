package org.geely.domain.core.data;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 67091
 */
@Data
public class SkuImageData implements Serializable {
    private Integer id;
    private Integer skuId;
    private String url;
    private Integer sort;
    private Integer version;
    public SkuImageData() {

    }
    public SkuImageData(Integer skuId, String url, Integer sort) {
        this.skuId = skuId;
        this.url = url;
        this.sort = sort;
        this.version = 0;
    }
}
