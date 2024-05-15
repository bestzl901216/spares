package org.geely.controller.dto.site;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("收款账户")
public class BankAccountDTO {
    @ApiModelProperty("公司名称")
    private String name;
    @ApiModelProperty("开户行")
    private String bank;
    @ApiModelProperty("银行账号")
    private String bankAccount;
}
