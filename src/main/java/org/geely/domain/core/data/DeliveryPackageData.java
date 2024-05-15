package org.geely.domain.core.data;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ricardo zhou
 */
@Data
public class DeliveryPackageData implements Serializable {
    private Integer id;
    /**
     * 发货单id
     */
    private Integer deliveryNoteId;
    /**
     * 销售订单id
     */
    private Integer saleOrderId;
    /**
     * 销售订单项id
     */
    private Integer saleOrderItemId;
    /**
     * 发货数量
     */
    private Integer quantity;
    /**
     * 版本号
     */
    private Integer version;
}
