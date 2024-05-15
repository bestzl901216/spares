package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("商家支付审批")
public class SupplierPayApproveDTO {
    @ApiModelProperty(value="支付单号", required = true)
    @NotBlank(message = "支付单号不能为空")
    @Length(max = 50)
    private String sn;
    @ApiModelProperty(value="是否已收款", required = true)
    @NotNull(message = "是否已收款不能为空")
    private boolean isSuccess;
    @ApiModelProperty(value="备注")
    @Length(max = 100)
    private String remark;
}
