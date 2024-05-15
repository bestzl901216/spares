package org.geely.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ricardo zhou
 */
@Data
public class CustomerSaleOrderItemDTO {
    @ApiModelProperty("销售订单子项id")
    private Integer id;
    @ApiModelProperty("商品id")
    private Integer productId;
    @ApiModelProperty("skuId")
    private Integer skuId;
    @ApiModelProperty("物料编码")
    private String materialCode;
    @ApiModelProperty("商品名称")
    private String productName;
    @ApiModelProperty("商品编号")
    private String productCode;
    @ApiModelProperty("商品图片")
    private String productImage;
    @ApiModelProperty("商品规格")
    private String skuSpecs;
    @ApiModelProperty("商品数量")
    private Integer quantity;
    @ApiModelProperty("单位")
    private String unit;
    @ApiModelProperty("商品单价")
    private BigDecimal price;
    @ApiModelProperty("商品总价")
    private BigDecimal amount;
    @ApiModelProperty("已发货数量")
    private Integer deliveryQuantity;
    @ApiModelProperty("已收货数量")
    private Integer receivedQuantity;
}
