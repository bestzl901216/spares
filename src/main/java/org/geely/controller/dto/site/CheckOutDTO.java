package org.geely.controller.dto.site;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.geely.common.enums.PayTypeEnum;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel("收银台")
public class CheckOutDTO {
    @ApiModelProperty("支付单号")
    private String sn;
    @ApiModelProperty("付款截止时间")
    private String deadline;
    @ApiModelProperty("支付金额")
    private BigDecimal amount;
    @ApiModelProperty("商家信息")
    private List<CheckOutSupplierDTO> suppliers;
    @ApiModelProperty("可用付款类型")
    private List<PayTypeEnum> payTypes;
}
