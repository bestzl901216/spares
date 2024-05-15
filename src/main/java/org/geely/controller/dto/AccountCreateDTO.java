package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author ricardo zhou
 */
@Data
@ApiModel("新建账号")
public class AccountCreateDTO {
    /**
     * 电话
     */
    @ApiModelProperty(value = "电话", required = true)
    @NotBlank(message = "电话不能为空")
    public String phone;
    /**
     * 密码（明文）
     */
    @ApiModelProperty(value = "密码（明文）", required = true)
    @NotBlank(message = "密码不能为空")
    public String password;
}
