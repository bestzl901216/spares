package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("渠道")
public class MarketChannelRelDTO {
    @ApiModelProperty("渠道id")
    public Integer marketChannelId;
    @ApiModelProperty("渠道名称")
    public String marketChannel;
    @ApiModelProperty("加价率")
    public BigDecimal markupRate;
}
