package org.geely.infrastructure.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单
 *
 * @author cong huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "after_sale_order", autoResultMap = true)
public class AfterSaleOrderDO extends BaseDO {
    private String saleOrderId;
    private String sn;
    private Integer afterSaleType;
    private Integer state;
    private BigDecimal amount;
    private BigDecimal amountReturn;
    private String memo;
    private LocalDateTime approveTime;
    private LocalDateTime amountReturnTime;
    private LocalDateTime finishedTime;
    private String images;
    private String consignee;
    private String consigneeAddress;
    private String consigneePhone;
    private String refuseReason;
    private Boolean isReverse;
    private Integer creatorId;
    private Integer approverId;
    private String approver;
}
