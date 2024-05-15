package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("SKU")
public class SkuUpdateDTO extends SkuCreateDTO
{
    @ApiModelProperty(value = "id,0为新增",required = true)
    @Min(0)
    @NotNull(message = "SKU_ID不能为空")
    private int id;
}
