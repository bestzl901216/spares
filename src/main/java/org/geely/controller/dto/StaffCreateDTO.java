package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author ricardo zhou
 */
@ApiModel("创建员工")
public class StaffCreateDTO {
    @ApiModelProperty(value = "手机号", required = true)
    @Length(min = 11, max = 11, message = "手机号长度不正确")
    public String phone;
    @ApiModelProperty(value = "角色名", required = true)
    @NotBlank(message = "角色名不能为空")
    public String roleName;
}
