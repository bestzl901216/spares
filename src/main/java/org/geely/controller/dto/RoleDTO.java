package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author ricardo zhou
 */
@ApiModel("角色")
public class RoleDTO {
    @ApiModelProperty("角色id")
    public Integer id;
    @ApiModelProperty("角色名称")
    public String name;
    @ApiModelProperty("备注")
    public String remark;
    @ApiModelProperty("权限数量")
    public Integer permissionCount;
    @ApiModelProperty("员工数量")
    public Integer staffCount;

}
