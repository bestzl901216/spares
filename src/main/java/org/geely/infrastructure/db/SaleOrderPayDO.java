package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.geely.common.enums.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单
 *
 * @author cong huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sale_order_pay", autoResultMap = true)
public class SaleOrderPayDO extends BaseDO {
    private Integer saleOrderId;
    private String sn;
    private Integer parentId;
    private PayTypeEnum payType;
    private BigDecimal amount;
    private String paySn;
    private OrderPayStateEnum state;
    @TableField(updateStrategy= FieldStrategy.IGNORED)
    private LocalDateTime payTime;
}
