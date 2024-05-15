package org.geely.controller.dto.site;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel("商家信息")
public class CheckOutSupplierDTO {
    @ApiModelProperty("商家id")
    private Integer id;
    @ApiModelProperty("商家名称")
    private String name;
    @ApiModelProperty("商家收款账户")
    private BankAccountDTO bankAccount;
    @ApiModelProperty("店铺信息")
    private List<CheckOutShopDTO> shops;
    @ApiModelProperty("商家支付金额")
    private BigDecimal amount;
}
