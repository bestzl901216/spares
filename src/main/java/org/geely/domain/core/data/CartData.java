package org.geely.domain.core.data;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ricardo zhou
 */
@Data
public class CartData implements Serializable {
    private Integer id;
    private Integer mallId;
    private Integer customerId;
    private Integer accountId;
    private Integer skuId;
    private Integer quantity;
    private Boolean selected;
    private Integer version;
}
