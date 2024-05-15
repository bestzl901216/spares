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
@ApiModel("添加品质")
public class ProductQualityCreateDTO {
    @ApiModelProperty(value = "品质", required = true)
    @NotBlank(message = "品质不能为空")
    @Length(max = 10)
    private String name;

    @ApiModelProperty(value = "备注")
    @Length(max = 100)
    private String remark;
}
