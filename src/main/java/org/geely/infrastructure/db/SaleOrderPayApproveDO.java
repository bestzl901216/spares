package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.geely.common.enums.OrderPayApproveStateEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单支付审核
 *
 * @author cong huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sale_order_pay_approve", autoResultMap = true)
public class SaleOrderPayApproveDO extends BaseDO {
    private Integer batchId;
    private Integer supplierId;
    private BigDecimal amount;
    private String paySn;
    private OrderPayApproveStateEnum state;
    private String remark;
    private String approveRemark;
    private String vouchers;
    private LocalDateTime approveTime;
}
