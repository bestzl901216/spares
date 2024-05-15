package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("SKU渠道价格")
public class SkuPriceDTO {
    @ApiModelProperty(value = "SKU渠道价格id")
    private Integer id;
    @ApiModelProperty(value = "渠道id")
    private Integer marketChannelId;
    @ApiModelProperty(value = "固定价格")
    private BigDecimal price;
}
