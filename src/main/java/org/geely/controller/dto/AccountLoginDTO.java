package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author ricardo zhou
 */
@Data
@ApiModel("登录账号")
public class AccountLoginDTO {
    /**
     * 电话
     */
    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank(message = "请输入正确的手机号")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "请输入正确的手机号")
    private String phone;
    /**
     * 密码（明文）
     */
    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码不能为空")
//    @Length(max = 200, min = 6, message = "请输入6-20位非空白字符密码")
    private String password;
}
