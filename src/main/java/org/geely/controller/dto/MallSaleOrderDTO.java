package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.geely.common.enums.DeliveryTypeEnum;
import org.geely.common.enums.OrderStateEnum;
import org.geely.common.enums.PayTypeEnum;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ricardo zhou
 */
@Data
@ApiModel("租户销售订单")
public class MallSaleOrderDTO {
    @ApiModelProperty("订单id")
    private Integer id;
    @ApiModelProperty("订单编号")
    private String sn;
    @ApiModelProperty("下单时间")
    private String createTime;
    @ApiModelProperty("支付金额")
    private BigDecimal payAmount;
    @ApiModelProperty("支付方式")
    private String payType;
    @ApiModelProperty("店铺名称")
    private String shopName;
    @ApiModelProperty("商家名称")
    private String supplierName;
    @ApiModelProperty("客户名称")
    private String customerName;
    @ApiModelProperty("客户下单账号名称")
    private String accountName;
    @ApiModelProperty("配送方式")
    private String deliveryType;
    @ApiModelProperty("订单状态")
    private Integer state;
    @ApiModelProperty("订单状态描述")
    private String stateDesc;
    @ApiModelProperty("订单明细")
    private List<CustomerSaleOrderDTO.SaleOrderItemDTO> items;

    public void setState(Integer state) {
        this.state = state;
        this.stateDesc = OrderStateEnum.valueOf(state).getName();
    }

    public void setPayType(String payType) {
        this.payType = PayTypeEnum.of(Integer.valueOf(payType)).getName();
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = DeliveryTypeEnum.of(Integer.valueOf(deliveryType)).getName();
    }

    @Data
    @ApiModel("销售订单明细")
    public static class SaleOrderItemDTO {
        @ApiModelProperty("商品名称")
        private String productName;
        @ApiModelProperty("商品编号")
        private String productCode;
        @ApiModelProperty("商品品质")
        private String productQuality;
        @ApiModelProperty("商品品牌")
        private String productBrand;
        @ApiModelProperty("商品规格")
        private String skuSpecs;
        @ApiModelProperty("商品单价")
        private BigDecimal price;
        @ApiModelProperty("商品数量")
        private Integer quantity;

    }
}
