package org.geely.controller.dto;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.geely.common.enums.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ricardo zhou
 */
@Data
@ApiModel("商家端-订单详情")
public class SupplierSaleOrderDetailDTO {
    @ApiModelProperty("订单id")
    private Integer id;
    @ApiModelProperty("订单号")
    private String sn;
    @ApiModelProperty("店铺名称")
    private String shopName;
    @ApiModelProperty("客户名称")
    private String customerName;
    @ApiModelProperty("状态")
    private Integer state;
    @ApiModelProperty("下单时间")
    private String createTime;
    @ApiModelProperty("取消时间")
    private String cancelTime;
    @ApiModelProperty("支付时间")
    private String payTime;
    @ApiModelProperty("发货时间")
    private String deliveryTime;
    @ApiModelProperty("收货时间")
    private String receivedTime;
    @ApiModelProperty("退款时间")
    private String amountReturnTime;
    @ApiModelProperty("完成时间")
    private String finishTime;
    @ApiModelProperty("自动取消时间")
    private String payDeadline;
    @ApiModelProperty("自动收货时间")
    private String receivedDeadline;
    @ApiModelProperty("自动完成时间")
    private String finishDeadline;
    @ApiModelProperty("取消来源")
    private OperateFromEnum cancelFrom;
    @ApiModelProperty("取消原因")
    private String cancelReason;
    @ApiModelProperty("买家留言")
    private String remark;
    @ApiModelProperty("收货人")
    private String receiver;
    @ApiModelProperty("收货人电话")
    private String receiverPhone;
    @ApiModelProperty("收货地址")
    private String receiverAddress;
    @ApiModelProperty("商品金额")
    private String amount;
    @ApiModelProperty("支付金额")
    private String payAmount;
    @ApiModelProperty("支付方式")
    private String payType;
    @ApiModelProperty("支付流水号")
    private String paySn;
    @ApiModelProperty("支付状态：0待支付，1待审批，2成功")
    private Integer payState;
    @ApiModelProperty("支付批次")
    private String payBatchSn;
    @ApiModelProperty("实际支付金额")
    private String approveAmount;
    @ApiModelProperty("支付备注")
    private String approveRemark;
    @ApiModelProperty("支付凭证,逗号分隔")
    private String approveVouchers;
    @ApiModelProperty("相关订单")
    private List<Order> orders;
    @ApiModelProperty("配送方式")
    private String deliveryType;
    @ApiModelProperty("运费方式")
    private String freightType;
    @ApiModelProperty("状态描述")
    private String stateDesc;
    @ApiModelProperty("状态提示")
    private String tip;
    @ApiModelProperty("关键节点")
    private List<VitalNodeDTO> vitalNodes = new ArrayList<>();

    public SupplierSaleOrderDetailDTO afterPropertiesSet() {
        this.vitalNodes();
        stateDescAndTip();
        PayTypeEnum payTypeEnum = PayTypeEnum.of(Integer.valueOf(this.payType));
        this.payType = payTypeEnum == null ? "" : payTypeEnum.getName();
        DeliveryTypeEnum deliveryTypeEnum = DeliveryTypeEnum.of(Integer.valueOf(this.deliveryType));
        this.deliveryType = deliveryTypeEnum == null ? "" : deliveryTypeEnum.getName();
        FreightTypeEnum freightTypeEnum = FreightTypeEnum.of(Integer.valueOf(this.freightType));
        this.freightType = freightTypeEnum == null ? "" : freightTypeEnum.getName();
        return this;
    }

    private void vitalNodes() {
        // 取消订单
        if (OrderStateEnum.CANCELED.getId().equals(this.state)) {
            VitalNodeDTO node1 = new VitalNodeDTO(1, "提交订单", createTime, StringUtils.isNotBlank(createTime));
            VitalNodeDTO node2 = new VitalNodeDTO(2, "取消订单", cancelTime, StringUtils.isNotBlank(cancelTime));
            vitalNodes = Lists.newArrayList(node1, node2);
            return;
        }
        // 已退款
        if (OrderStateEnum.REFUNDED.getId().equals(this.state)) {
            VitalNodeDTO node1 = new VitalNodeDTO(1, "提交订单", createTime, StringUtils.isNotBlank(createTime));
            VitalNodeDTO node2 = new VitalNodeDTO(2, "买家付款", payTime, StringUtils.isNotBlank(payTime));
            VitalNodeDTO node3 = new VitalNodeDTO(3, "订单退款", amountReturnTime, StringUtils.isNotBlank(amountReturnTime));
            vitalNodes = Lists.newArrayList(node1, node2, node3);
            return;
        }
        // 未退款  未取消
        VitalNodeDTO node1 = new VitalNodeDTO(1, "提交订单", createTime, StringUtils.isNotBlank(createTime));
        VitalNodeDTO node2 = new VitalNodeDTO(2, "买家付款", payTime, StringUtils.isNotBlank(payTime));
        VitalNodeDTO node3 = new VitalNodeDTO(3, "发货完成", deliveryTime, StringUtils.isNotBlank(deliveryTime));
        VitalNodeDTO node4 = new VitalNodeDTO(4, "收货完成", receivedTime, StringUtils.isNotBlank(receivedTime));
        VitalNodeDTO node5 = new VitalNodeDTO(5, "订单完成", finishTime, StringUtils.isNotBlank(finishTime));
        vitalNodes = Lists.newArrayList(node1, node2, node3, node4, node5);
    }

    private void stateDescAndTip() {
        OrderStateEnum orderStateEnum = OrderStateEnum.valueOf(this.state);
        switch (orderStateEnum) {
            case TO_BE_PAID:
                this.stateDesc = "待付款";
                this.tip = String.format("订单将在%s后自动取消", formatSurplusTime(this.payDeadline));
                break;
            case TO_BE_APPROVED:
                this.stateDesc = "待审核";
                this.tip = "买家已线下付款，等待财务审批";
                break;
            case TO_BE_SYNC:
                this.stateDesc = "待同步";
                this.tip = "订单同步中，请耐心等待";
                break;
            case SYNC_FAILED:
                this.stateDesc = "同步失败";
                this.tip = "失败原因：sap接口请求异常";
                break;
            case TO_BE_SHIPPED:
                this.stateDesc = "待发货";
                this.tip = "买家已付款，请及时发货";
                break;
            case TO_BE_RECEIVED:
                this.stateDesc = "待收货";
                this.tip = "所有商品已发货，等待客户签收发单";
                break;
            case RECEIVED:
                this.stateDesc = "已收货";
                this.tip = String.format("买家已收货，售后保障中，%s后失效", formatSurplusTime(this.finishDeadline));
                break;
            case FINISHED:
                this.stateDesc = "已完成";
                this.tip = "已过售后保障期，订单完成";
                break;
            case CANCELED:
                this.stateDesc = "已取消";
                this.tip = this.cancelFrom == OperateFromEnum.SYSTEM ? "支付超时，订单自动取消" :
                        this.cancelFrom == OperateFromEnum.BUYER ? "客户取消订单，不想要了" : "商家取消订单，没货了";
                break;
            case REFUNDED:
                this.stateDesc = "已退款";
                this.tip = "所有商品已退款";
                break;
            default:
                this.stateDesc = "未知状态";
                this.tip = "未知状态";
        }
    }


    @Data
    @AllArgsConstructor
    public static class VitalNodeDTO {
        private Integer index;
        private String desc;
        private String time;
        private Boolean isDone;
    }

    @Data
    @ApiModel("订单支付信息")
    public static class Order {
        @ApiModelProperty("销售订单编号")
        private String sn;
        @ApiModelProperty("子流水号")
        private String paySn;
    }

    private static String formatSurplusTime(String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.now();
        LocalDateTime endDateTime = LocalDateTime.parse(endTime, formatter);
        // 获取两个时间之间的间隔，并格式化为天、小时、分钟、秒
        String s = "";
        long days = ChronoUnit.DAYS.between(startDateTime, endDateTime);
        if (days > 0) {
            s += days + "天";
        }
        long hours = ChronoUnit.HOURS.between(startDateTime, endDateTime) % 24;
        if (days > 0 || hours > 0) {
            s += hours + ":";
        }
        long minutes = ChronoUnit.MINUTES.between(startDateTime, endDateTime) % 60;
        s += minutes + ":";
        long seconds = ChronoUnit.SECONDS.between(startDateTime, endDateTime) % 60;
        s += seconds;
        return s;
    }
}
