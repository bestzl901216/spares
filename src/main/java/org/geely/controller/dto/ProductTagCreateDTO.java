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
@ApiModel("添加标签")
public class ProductTagCreateDTO {
    @ApiModelProperty(value = "标签", required = true)
    @NotBlank(message = "标签不能为空")
    @Length(max = 10)
    private String name;

    @ApiModelProperty(value = "备注")
    @Length(max = 100)
    private String remark;

    @ApiModelProperty(value = "字体颜色，rgb")
    @Length(max = 6)
    private String fontColor;
    @ApiModelProperty(value = "背景颜色，rgb")
    @Length(max = 6)
    private String backgroundColor;
}
