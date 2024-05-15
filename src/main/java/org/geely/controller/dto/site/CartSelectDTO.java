package org.geely.controller.dto.site;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("修改购物车商品选中状态")
public class CartSelectDTO {
    @ApiModelProperty(value = "id", required = true)
    @NotNull
    private Integer id;
    @ApiModelProperty(value = "是否选中", required = true)
    @NotNull
    private Boolean selected;
}
