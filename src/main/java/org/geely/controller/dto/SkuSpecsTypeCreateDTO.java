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
@ApiModel("添加Sku规格类型")
public class SkuSpecsTypeCreateDTO {
    @ApiModelProperty(value = "规格类型名称", required = true)
    @NotBlank
    @Length(max = 10)
    private String name;
}
