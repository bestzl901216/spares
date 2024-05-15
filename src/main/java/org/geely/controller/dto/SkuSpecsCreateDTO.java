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
@ApiModel("添加Sku规格")
public class SkuSpecsCreateDTO {
    @ApiModelProperty(value = "规格类型id", required = true)
    private Integer typeId;

    @ApiModelProperty(value = "规格值", required = true)
    @NotBlank
    @Length(max = 10)
    private String name;
}
