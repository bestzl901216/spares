package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ricardo zhou
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "delivery_package", autoResultMap = true)
public class DeliveryPackageDO extends BaseDO {
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
}
