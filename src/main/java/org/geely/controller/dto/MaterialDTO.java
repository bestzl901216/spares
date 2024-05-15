package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ricardo zhou
 */
@Data
@ApiModel("物料")
public class MaterialDTO {
    @ApiModelProperty(value = "物料id")
    private Integer id;
    @ApiModelProperty(value = "租户id")
    private Integer mallId;
    @ApiModelProperty(value = "商家id")
    private Integer supplierId;
    @ApiModelProperty(value = "物料编码")
    private String code;
    @ApiModelProperty(value = "物料名称")
    private String name;
    @ApiModelProperty(value = "oe编码")
    private String oeNo;
    @ApiModelProperty(value = "后台类目")
    private String category;
    @ApiModelProperty(value = "库存")
    private Integer stock;
    @ApiModelProperty(value = "单位")
    private String unit;
    @ApiModelProperty(value = "基础价格")
    private BigDecimal price;
    @ApiModelProperty(value = "更新时间")
    private String updateTime;
    @ApiModelProperty(value = "店铺")
    private String shopNames;
}
