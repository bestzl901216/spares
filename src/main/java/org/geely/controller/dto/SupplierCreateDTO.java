package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author cong huang
 */
@Data
@ApiModel("创建商家")
public class SupplierCreateDTO {
    @ApiModelProperty(value = "商家名称", required = true)
    @NotBlank(message = "企业名称不能为空")
    @Length(max = 50)
    private String name;

    @ApiModelProperty(value = "商户号")
    @Length(max = 50)
    private String merchantNo;

    @ApiModelProperty(value = "管理员手机号", required = true)
    @NotNull(message = "管理员手机号不能为空")
    @Length(max = 11)
    private String adminPhone;

    @ApiModelProperty(value = "收款账户", required = true)
    @NotNull(message = "收款账户不能为空")
    private Integer bankAccountId;

    @ApiModelProperty(value = "销售渠道")
    private Set<Integer> marketChannels;

    @ApiModelProperty(value = "sap类型")
    private Integer sapId;
}
