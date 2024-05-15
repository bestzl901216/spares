package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author ricardo zhou
 */
@Data
@ApiModel("更新账号")
public class AccountUpdateDTO {
    @ApiModelProperty(value = "账号名称")
    @NotBlank(message = "账号名称不能为空")
    public String name;
}
