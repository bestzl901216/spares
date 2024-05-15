package org.geely.controller.dto.site;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ricardo zhou
 */
@Data
@ApiModel("发货包裹")
public class CustomerDeliveryPackageDTO {
    @ApiModelProperty("商品名称")
    private String productName;
    @ApiModelProperty("商品规格")
    private String skuSpecs;
    @ApiModelProperty("商品图片")
    private String image;
    @ApiModelProperty("商品编码")
    private String productCode;
    @ApiModelProperty("物料编码")
    private String materialCode;
    @ApiModelProperty("数量")
    private Integer quantity;
    @ApiModelProperty("单位")
    private String unit;
    @ApiModelProperty("单价")
    private BigDecimal price;
    @ApiModelProperty("总价")
    private BigDecimal amount;
}
