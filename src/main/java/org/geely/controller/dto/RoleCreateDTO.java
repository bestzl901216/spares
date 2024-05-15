package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 角色创建DTO
 *
 * @author ricardo zhou
 */
@ApiModel("角色创建DTO")
public class RoleCreateDTO {

    @ApiModelProperty(value = "角色名称", required = true)
    @NotBlank
    @Length(max = 10)
    public String name;
    @ApiModelProperty(value = "角色备注")
    @Length(max = 50)
    public String remark;
    @ApiModelProperty(value = "角色权限集合")
    public List<String> permissions;
}
