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
@TableName(value = "sale_order", autoResultMap = true)
public class SaleOrderDO extends BaseDO {
    private Integer mallId;
    private Integer supplierId;
    private Integer shopId;
    private Integer customerId;
    private Integer customerAccountId;
    private String sn;
    private String receiver;
    private String receiverProvince;
    private String receiverCity;
    private String receiverArea;
    private String receiverAddress;
    private String receiverPhone;
    private OrderStateEnum state;
    private BigDecimal amount;
    private BigDecimal payAmount;
    private FreightTypeEnum freightType;
    private DeliveryTypeEnum deliveryType;
    @TableField(updateStrategy= FieldStrategy.IGNORED)
    private LocalDateTime payTime;
    private LocalDateTime approvedTime;
    private LocalDateTime deliveryTime;
    private LocalDateTime receivedTime;
    private LocalDateTime finishedTime;
    private LocalDateTime cancelTime;
    private String cancelReason;
    private Integer cancelBy;
    private OperateFromEnum cancelFrom;
    private LocalDateTime payDeadline;
    private LocalDateTime receivedDeadline;
    private LocalDateTime finishedDeadline;
    private PayTypeEnum payType;
    private Integer invoiceId;
    private BigDecimal taxRate;
    private String remark;
    private String sapSn;
    private Boolean isReverse;
    private Integer creatorId;
}
