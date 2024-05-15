package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.geely.common.enums.DeliveryTypeEnum;
import org.geely.common.enums.OrderStateEnum;
import org.geely.common.enums.PayTypeEnum;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author ricardo zhou
 */
@Data
@ApiModel("商家端-订单")
public class SupplierSaleOrderDTO {

    @ApiModelProperty("支付批次id")
    private Integer id;
    @ApiModelProperty("支付批次号")
    private String payBatchSn;
    @ApiModelProperty("支付时间")
    private String payTime;
    @ApiModelProperty("支付状态：0待支付，1待审批，2成功")
    private Integer payState;
    @ApiModelProperty("支付金额")
    private BigDecimal amount;
    @ApiModelProperty("实付金额")
    private BigDecimal payAmount;
    @ApiModelProperty("订单列表")
    private List<LinkedHashMap<String, Object>> orders;

    public void setOrders(List<LinkedHashMap<String, Object>> orders) {
        orders.forEach(e -> {
            e.put("stateDesc", OrderStateEnum.valueOf((Integer) e.get("state")).getName());
            e.put("deliveryType", DeliveryTypeEnum.of((Integer) e.get("deliveryType")).getName());
            e.put("payType", PayTypeEnum.of((Integer) e.get("payType")).getName());
        });
        this.orders = orders;
    }


    @Data
    @ApiModel("商家端-销售订单")
    public static class SaleOrder {
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
        private List<SaleOrderItem> items;

    }

    @Data
    @ApiModel("商家端-销售订单明细")
    public static class SaleOrderItem {
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
        @ApiModelProperty("商品图片")
        private String image;
    }
}
