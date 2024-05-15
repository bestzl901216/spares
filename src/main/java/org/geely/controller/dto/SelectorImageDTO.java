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
@ApiModel("图片下拉框")
public class SelectorImageDTO extends SelectorDTO {
    @ApiModelProperty("image")
    private String image;
    public SelectorImageDTO(String name, String value, String image){
        setName(name);
        setValue(value);
        setImage(image);
    }
}
