package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cong huang
 */
@Data
@ApiModel("下拉框")
public class SelectorDTO {
    @ApiModelProperty("name")
    private String name;
    @ApiModelProperty("value")
    private String value;
    public SelectorDTO (){
    }
    public SelectorDTO(String name , String value) {
        this.name = name;
        this.value = value;
    }
}
