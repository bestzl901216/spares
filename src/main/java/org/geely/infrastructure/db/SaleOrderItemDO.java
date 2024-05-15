package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 订单
 *
 * @author cong huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sale_order_item", autoResultMap = true)
public class SaleOrderItemDO extends BaseDO {
    private Integer saleOrderId;
    private Integer productId;
    private Integer skuId;
    private String skuSpecs;
    private String productName;
    private String productCode;
    private String skuCode;
    private String materialCode;
    private String productQuality;
    private String productBrand;
    private BigDecimal originPrice;
    private BigDecimal price;
    private Integer quantity;
    private Boolean supportReturn;
    private String unit;
    private Integer deliveryQuantity;
    private Integer receivedQuantity;
    private String image;
}
