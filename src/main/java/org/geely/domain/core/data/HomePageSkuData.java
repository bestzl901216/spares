package org.geely.domain.core.data;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ricardo zhou
 */
@Data
public class HomePageSkuData implements Serializable {
    private Integer id;
    private Integer mallId;
    private Integer c1Id;
    private Integer skuId;
    private Integer sort;
    private Integer version;
}
