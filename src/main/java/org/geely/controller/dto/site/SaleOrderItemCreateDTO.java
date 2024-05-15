package org.geely.controller.dto.site;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author cong huang
 */
@Data
@ApiModel("订单子项")
public class SaleOrderItemCreateDTO {
    @ApiModelProperty(value = "SKU_id", required = true)
    @NotNull(message = "sku_id不能为空")
    private Integer skuId;
    @ApiModelProperty(value = "数量", required = true)
    @NotNull(message = "数量不能为空")
    private Integer quantity;
    @ApiModelProperty(value = "价格", required = true)
    @NotNull(message = "价格不能为空")
    private BigDecimal price;
}

