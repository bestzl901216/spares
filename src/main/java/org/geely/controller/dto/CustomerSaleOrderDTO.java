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
 * 商城端销售订单列表项
 *
 * @author ricardo zhou
 */
@Data
@ApiModel("销售订单")
public class CustomerSaleOrderDTO {
    @ApiModelProperty("订单id")
    private Integer id;
    @ApiModelProperty("订单编号")
    private String sn;
    @ApiModelProperty("下单时间")
    private String createTime;
    @ApiModelProperty("支付金额")
    private BigDecimal payAmount;
    @ApiModelProperty("订单金额")
    private BigDecimal amount;
    @ApiModelProperty("支付方式")
    private String payType;
    @ApiModelProperty("店铺名称")
    private String shopName;
    @ApiModelProperty("配送方式")
    private String deliveryType;
    @ApiModelProperty("订单状态")
    private Integer state;
    @ApiModelProperty("订单状态描述")
    private String stateDesc;
    @ApiModelProperty("订单明细")
    private List<SaleOrderItemDTO> items;

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
        @ApiModelProperty("商品单位")
        private String unit;
        @ApiModelProperty("商品图片")
        private String image;
        @ApiModelProperty("skuId")
        private Integer skuId;

    }
}
