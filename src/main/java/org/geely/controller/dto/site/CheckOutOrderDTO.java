package org.geely.controller.dto.site;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("订单信息")
public class CheckOutOrderDTO {
    @ApiModelProperty("订单id")
    private Integer id;
    @ApiModelProperty("订单sn")
    private String sn;
    @ApiModelProperty("订单金额")
    private BigDecimal amount;
    public CheckOutOrderDTO () {
    }
    public CheckOutOrderDTO (Integer id, String sn, BigDecimal amount) {
        this.id = id;
        this.sn = sn;
        this.amount = amount;
    }
}
