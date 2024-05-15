package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * @author ricardo zhou
 */
@Data
@ApiModel("新建租户")
public class MallCreateDTO {
    @ApiModelProperty(value = "租户名称", required = true)
    @NotBlank(message = "名称不能为空")
    private String name;

    @ApiModelProperty(value = "管理员手机号", required = true)
    @NotBlank(message = "管理员手机号不能为空")
    private String adminPhone;

    @ApiModelProperty(value = "渠道", required = true)
    private Set<Integer> marketChannels;
}
