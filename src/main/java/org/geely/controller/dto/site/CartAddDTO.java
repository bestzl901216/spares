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
@ApiModel("加入购物车")
public class CartAddDTO {
    @ApiModelProperty(value = "SKU_id", required = true)
    @NotNull(message = "SKU_id不能为空")
    private Integer skuId;
    @Min(1)
    @Max(99999)
    @ApiModelProperty(value = "数量", required = true)
    @NotNull(message = "数量不能为空")
    private Integer quantity;
}

