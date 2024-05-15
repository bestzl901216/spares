package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("SKU规格")
public class SkuSpecsDTO {
    @ApiModelProperty(value = "SKU规格类型id")
    private Integer typeId;
    @ApiModelProperty(value = "SKU规格类型")
    private String typeName;
    @ApiModelProperty(value = "SKU规格名称")
    private String name;
}
