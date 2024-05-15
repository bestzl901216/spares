package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.geely.common.model.PageDTO;
import org.hibernate.validator.constraints.Length;

/**
 * @author cong huang
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("收款账户查询")
public class MallBankAccountPageDTO extends PageDTO {
    @ApiModelProperty(hidden = true)
    private Integer mallId;
    @ApiModelProperty(value = "企业名称")
    @Length(max = 50)
    private String name;
    @ApiModelProperty(value = "开户行")
    @Length(max = 50)
    private String bank;
    @ApiModelProperty(value = "银行账号")
    @Length(max = 50)
    private String bankAccount;
}
