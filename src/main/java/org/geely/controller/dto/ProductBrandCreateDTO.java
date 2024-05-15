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
@ApiModel("添加品牌")
public class ProductBrandCreateDTO {
    @ApiModelProperty(value = "品牌", required = true)
    @NotBlank(message = "品牌不能为空")
    @Length(max = 10)
    private String name;

    @ApiModelProperty(value = "Logo")
    private String logo;
}
