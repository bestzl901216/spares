package org.geely.controller.dto.site;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("商家付款凭证")
public class OrderPaySupplierApproveDTO {
    @ApiModelProperty(value = "商家id", required = true)
    @NotNull(message = "商家id不能为空")
    private Integer supplierId;
    @ApiModelProperty(value = "支付备注")
    @Length(max = 100)
    private String remark;
    @ApiModelProperty(value = "'支付凭证图片url，逗号分隔", required = true)
    @NotBlank(message = "支付凭证图片不能为空")
    private String vouchers;
}
