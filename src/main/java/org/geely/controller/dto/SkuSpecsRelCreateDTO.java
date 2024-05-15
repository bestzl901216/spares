package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("SKU规格")
public class SkuSpecsRelCreateDTO {
    @ApiModelProperty(value = "规格类型", required = true)
    @NotNull(message = "规格类型不能为空")
    private Integer specsTypeId;
    @ApiModelProperty(value = "规格值", required = true)
    @NotBlank(message = "规格值不能为空")
    @Length(max = 10, message = "规格值长度不能超过10")
    private String specs;
}
