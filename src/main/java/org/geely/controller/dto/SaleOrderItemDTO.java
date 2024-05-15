package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 销售订单明细
 *
 * @author ricardo zhou
 */
@Data
@ApiModel("销售订单子项明细")
public class SaleOrderItemDTO {
    @ApiModelProperty("销售订单子项id")
    public Integer id;
    @ApiModelProperty("商品id")
    public Integer productId;
    @ApiModelProperty("skuId")
    public Integer skuId;
    @ApiModelProperty("物料编码")
    public String materialCode;
    @ApiModelProperty("商品名称")
    public String productName;
    @ApiModelProperty("商品编号")
    public String productCode;
    @ApiModelProperty("商品图片")
    public String productImage;
    @ApiModelProperty("商品规格")
    public String skuSpecs;
    @ApiModelProperty("商品数量")
    public Integer quantity;
    @ApiModelProperty("单位")
    public String unit;
    @ApiModelProperty("商品单价")
    public BigDecimal price;
    @ApiModelProperty("商品总价")
    public BigDecimal amount;
    @ApiModelProperty("已发货数量")
    public Integer deliveryQuantity;
    @ApiModelProperty("已收货数量")
    public Integer receivedQuantity;
}
