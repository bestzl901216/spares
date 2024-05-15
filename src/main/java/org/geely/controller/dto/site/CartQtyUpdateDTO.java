package org.geely.controller.dto.site;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author cong huang
 */
@Data
@ApiModel("修改购物车商品数量")
public class CartQtyUpdateDTO {
    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "购物车id不能为空")
    private Integer id;
    @ApiModelProperty(value = "数量", required = true)
    @Min(1)
    @Max(99999)
    @NotNull(message = "数量不能为空")
    private Integer quantity;
}

