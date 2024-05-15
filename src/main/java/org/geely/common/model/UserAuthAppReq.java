package org.geely.common.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Date 2023/10/11 09:31
 * @Author chaoming.zhu
 */
@Data
@ApiModel(value = "查询用户授权的应用")
public class UserAuthAppReq {

    @ApiModelProperty(value = "用户名登录用户信息username参数")
    @NotNull(message = "用户名登录用户信息username参数不能为空")
    private String externalId;

    @ApiModelProperty(value = "应用id")
    @NotNull(message = "应用id不能为空")
    private String appId;

}