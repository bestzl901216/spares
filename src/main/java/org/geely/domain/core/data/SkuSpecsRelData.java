package org.geely.domain.core.data;

import lombok.Data;

import java.io.Serializable;

@Data
public class SkuSpecsRelData implements Serializable {
    private Integer id;
    private Integer skuId;
    private Integer specsTypeId;
    private String specs;
    private Integer version;
    public SkuSpecsRelData(){

    }
    public SkuSpecsRelData(Integer skuId, Integer specsTypeId, String specs){
        this.skuId = skuId;
        this.specsTypeId = specsTypeId;
        this.specs = specs;
        this.version = 0;
    }
}
