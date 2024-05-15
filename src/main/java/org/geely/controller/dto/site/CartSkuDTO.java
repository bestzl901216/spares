package org.geely.controller.dto.site;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("购物车商品")
public class CartSkuDTO extends SkuOrderingDTO {
    @ApiModelProperty("购物车id")
    private Integer id;
    @ApiModelProperty("购物车数量")
    private Integer quantity;
    @ApiModelProperty("是否选中")
    private Boolean selected;
}
