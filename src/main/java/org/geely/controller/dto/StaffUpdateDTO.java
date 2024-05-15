package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @author ricardo zhou
 */
@ApiModel("编辑员工")
public class StaffUpdateDTO {
    @ApiModelProperty(value = "角色名", required = true)
    @NotBlank(message = "角色名不能为空")
    public String roleName;
}
