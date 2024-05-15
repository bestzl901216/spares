package org.geely.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class PageDTO {

    @ApiModelProperty(value = "页码，默认1")
    @Min(1)
    private Integer current =1;

    @ApiModelProperty(value = "每页大小，默认10")
    @Min(1)
    @Max(1000)
    private Integer size =10;

    public void setCurrent(Integer current) {
        if (current != null) {
            this.current = current;
        }
    }
    public void setSize(Integer size) {
        if (size != null) {
            this.size = size;
        }
    }
}
