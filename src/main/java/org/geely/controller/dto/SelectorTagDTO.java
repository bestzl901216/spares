package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author cong huang
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("标签下拉框")
public class SelectorTagDTO extends SelectorDTO {
    @ApiModelProperty("字体颜色")
    private String fontColor;
    @ApiModelProperty("背景颜色")
    private String backgroundColor;
    public SelectorTagDTO(String name, String value, String fontColor, String backgroundColor){
        setName(name);
        setValue(value);
        setFontColor(fontColor);
        setBackgroundColor(backgroundColor);
    }
}
