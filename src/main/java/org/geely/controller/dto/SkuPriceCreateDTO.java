package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("渠道价格")
public class SkuPriceCreateDTO {
    @ApiModelProperty(value = "渠道id", required = true)
    private Integer marketChannelId;
    @ApiModelProperty(value = "固定价格", required = true)
    private BigDecimal price;
}
