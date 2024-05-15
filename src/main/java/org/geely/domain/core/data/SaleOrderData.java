package org.geely.domain.core.data;

import lombok.Data;
import org.geely.common.enums.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author ricardo zhou
 */
@Data
public class SaleOrderData implements Serializable {
    private Integer id;
    /**
     * 租户id
     */
    private Integer mallId;
    /**
     * 商家id
     */
    private Integer supplierId;
    /**
     * 店铺id
     */
    private Integer shopId;
    /**
     * 客户id
     */
    private Integer customerId;
    /**
     * 客户下单员工账号id
     */
    private Integer customerAccountId;
    /**
     * 订单编号
     */
    private String sn;
    /**
     * 收货人
     */
    private String receiver;
    /**
     * 收货区域代码
     */
    private String receiverPcaCode;
    /**
     * 收货地址省
     */
    private String receiverProvince;
    /**
     * 收货地址市
     */
    private String receiverCity;
    /**
     * 收货地址区
     */
    private String receiverArea;
    /**
     * 收货地址详情
     */
    private String receiverAddress;
    /**
     * 收货电话
     */
    private String receiverPhone;
    /**
     * 订单状态
     */
    private OrderStateEnum state;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 实付金额
     */
    private BigDecimal payAmount;
    /**
     * 运费支付方式
     */
    private FreightTypeEnum freightType;
    /**
     * 发货方式
     */
    private DeliveryTypeEnum deliveryType;
    /**
     * 支付时间
     */
    private LocalDateTime payTime;
    /**
     * 审批时间
     */
    private LocalDateTime approvedTime;
    /**
     * 发货时间
     */
    private LocalDateTime deliveryTime;
    /**
     * 收货时间
     */
    private LocalDateTime receivedTime;
    /**
     * 完成时间
     */
    private LocalDateTime finishedTime;
    /**
     * 取消时间
     */
    private LocalDateTime cancelTime;
    /**
     * 取消原因
     */
    private String cancelReason;
    /**
     * 取消账户id
     */
    private Integer cancelBy;
    /**
     * 取消来源
     */
    private OperateFromEnum cancelFrom;
    /**
     * 自动取消时间
     */
    private LocalDateTime payDeadline;
    /**
     * 自动收货时间
     */
    private LocalDateTime receivedDeadline;
    /**
     * 自动完成时间
     */
    private LocalDateTime finishedDeadline;
    /**
     * 支付方式
     */
    private PayTypeEnum payType;
    /**
     * 发票id
     */
    private Integer invoiceId;
    /**
     * 税率
     */
    private BigDecimal taxRate;
    /**
     * 备注
     */
    private String remark;
    /**
     * sap订单号
     */
    private String sapSn;
    /**
     * 是否反向订单
     */
    private Boolean isReverse;
    /**
     * 反向订单制单账号id
     */
    private Integer creatorId;
    /**
     * 版本号
     */
    private Integer version;
}
