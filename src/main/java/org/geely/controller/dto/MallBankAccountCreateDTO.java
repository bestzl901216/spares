package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author cong huang
 */
@Data
@ApiModel("创建收款账户")
public class MallBankAccountCreateDTO {
    @ApiModelProperty(value = "企业名称", required = true)
    @NotBlank(message = "企业名称不能为空")
    @Length(max = 50)
    private String name;

    @ApiModelProperty(value = "开户行", required = true)
    @NotBlank(message = "开户行不能为空")
    @Length(max = 50)
    private String bank;
    @ApiModelProperty(value = "银行账户", required = true)
    @NotBlank(message = "银行账户不能为空")
    @Length(max = 50)
    private String bankAccount;
}
