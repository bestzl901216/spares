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
@ApiModel("商品分类")
public class ProductCategoryCreateDTO {
    @ApiModelProperty(value = "父id")
    private Integer parentId;
    @ApiModelProperty(value = "名称", required = true)
    @NotBlank(message = "名称不能为空")
    @Length(max = 10)
    private String name;
}
