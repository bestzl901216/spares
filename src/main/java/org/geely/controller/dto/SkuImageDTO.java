package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("SKU图片")
public class SkuImageDTO {
    @ApiModelProperty(value = "SKU图片id")
    private Integer id;
    @ApiModelProperty(value = "SKU编号")
    private Integer skuId;
    @ApiModelProperty(value = "图片地址")
    private String url;
    private Integer sort;
}
