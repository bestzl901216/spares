package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ricardo zhou
 */
@Data
@ApiModel("员工")
public class StaffDTO {
    @ApiModelProperty("员工账号ID")
    public Integer id;
    @ApiModelProperty("员工账号名称")
    public String name;
    @ApiModelProperty("员工账号手机号")
    public String phone;
    @ApiModelProperty("员工角色名")
    public String roleName;
    @ApiModelProperty("员工账号创建时间")
    public String createTime;
    @ApiModelProperty("员工账号状态")
    public Integer state;

}
