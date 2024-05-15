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
@ApiModel("商品规格")
public class ProductPropsCreateDTO {
    @ApiModelProperty(value = "规格名称", required = true)
    @NotBlank(message = "规格名称不能为空")
    @Length(max = 20)
    private String name;

    @ApiModelProperty(value = "规格值", required = true)
    @NotBlank(message = "规格值不能为空")
    @Length(max = 200)
    private String value;
}
