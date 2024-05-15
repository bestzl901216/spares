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
 * 销售订单
 *
 * @author ricardo zhou
 */
@Data
@ApiModel("销售订单")
public class SaleOrderDTO {
    @ApiModelProperty("订单id")
    public Integer id;
    @ApiModelProperty("订单编号")
    public String sn;
    @ApiModelProperty("sap销售单号")
    public String sapSn;
    @ApiModelProperty("下单时间")
    public String createTime;
    @ApiModelProperty("支付金额")
    public BigDecimal payAmount;
    @ApiModelProperty("订单金额")
    public BigDecimal amount;
    @ApiModelProperty("支付方式")
    public String payType;
    @ApiModelProperty("客户名称")
    public String customerName;
    @ApiModelProperty("客户账号名称")
    public String customerAccountName;
    @ApiModelProperty("配送方式")
    public String deliveryType;
    @ApiModelProperty("订单状态")
    public Integer state;
    @ApiModelProperty("订单状态描述")
    public String stateDesc;
    @ApiModelProperty("订单明细")
    public List<SaleOrderItemDTO> items;

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
        public String productName;
        @ApiModelProperty("商品编号")
        public String productCode;
        @ApiModelProperty("商品品质")
        public String productQuality;
        @ApiModelProperty("商品品牌")
        public String productBrand;
        @ApiModelProperty("商品单价")
        public BigDecimal price;
        @ApiModelProperty("商品数量")
        public Integer quantity;
        @ApiModelProperty("商品图片")
        public String image;
        @ApiModelProperty("商品规格")
        public String skuSpecs;
    }
}
