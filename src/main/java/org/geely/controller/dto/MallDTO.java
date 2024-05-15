package org.geely.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ricardo zhou
 */
@Data
public class MallDTO {
    @ApiModelProperty(value = "租户id")
    private Integer id;
    @ApiModelProperty(value = "租户名称")
    private String name;
    @ApiModelProperty(value = "商户号")
    private String merchantNo;
    @ApiModelProperty(value = "管理员")
    private String admin;
    @ApiModelProperty(value = "销售渠道")
    private String marketChannels;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "状态")
    private Integer state;

}
