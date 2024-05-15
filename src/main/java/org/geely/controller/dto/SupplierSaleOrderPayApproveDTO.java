package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.geely.common.enums.PayTypeEnum;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ricardo zhou
 */
@Data
@ApiModel("商家后台-销售订单支付明细")
public class SupplierSaleOrderPayApproveDTO {
    @ApiModelProperty("支付批次")
    private String payBatchSn;
    @ApiModelProperty("支付金额")
    private BigDecimal amount;
    @ApiModelProperty("支付时间")
    private String payTime;
    @ApiModelProperty("支付方式")
    private String payType;
    @ApiModelProperty("支付流水号")
    private String paySn;
    @ApiModelProperty("支付凭证，逗号分隔")
    private String vouchers;
    @ApiModelProperty("支付备注")
    private String remark;
    @ApiModelProperty("相关订单")
    private List<Order> orders;

    public void setPayType(Integer payType) {
        this.payType = PayTypeEnum.of(payType).getName();
    }

    @Data
    @ApiModel("订单支付信息")
    public static class Order {
        @ApiModelProperty("销售订单编号")
        private String sn;
        @ApiModelProperty("子流水号")
        private String paySn;
    }
}
