package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单
 *
 * @author cong huang
 */
@Data
@TableName(value = "after_sale_order_item", autoResultMap = true)
public class AfterSaleOrderItemDO extends BaseDO {
    private Integer alterSaleOrderId;
    private Integer saleOrderItemId;
    private Integer quantity;
    private BigDecimal amount;
}
