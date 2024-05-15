package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("商品属性")
public class ProductPropsDTO {
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "属性名称")
    private String name;
    @ApiModelProperty(value = "属性值")
    private String value;
}
