package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ricardo zhou
 */
@Data
@ApiModel("设置")
public class SettingDTO {
    @ApiModelProperty("平台名称")
    private String name;
    @ApiModelProperty("宣传标语")
    private String motto;
    @ApiModelProperty("服务热线")
    private String servicePhone;
    @ApiModelProperty("logo")
    private String logo;
}
