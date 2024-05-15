package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ricardo zhou
 */
@Data
@ApiModel("物料后台类目")
public class BackendCategoryDTO {
    @ApiModelProperty(value = "名称")
    public String name;
}
