package org.geely.controller.dto.site;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel("店铺信息")
public class CheckOutShopDTO {
    @ApiModelProperty("店铺id")
    private Integer id;
    @ApiModelProperty("店铺名称")
    private String name;
    @ApiModelProperty("订单信息")
    private List<CheckOutOrderDTO> orders;
    @ApiModelProperty("店铺支付金额")
    private BigDecimal amount;
}
