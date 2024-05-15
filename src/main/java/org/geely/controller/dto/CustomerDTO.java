package org.geely.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ricardo zhou
 */
@Data
public class CustomerDTO {
    @ApiModelProperty(value = "客户编码")
    public Integer id;
    @ApiModelProperty(value = "名称")
    public String adminName;
    @ApiModelProperty(value = "手机号")
    public String adminPhone;
    @ApiModelProperty(value = "公司名称")
    public String name;
    @ApiModelProperty(value = "注册日期")
    public String createTime;
    @ApiModelProperty(value = "渠道")
    public String marketChannel;
    @ApiModelProperty(value = "状态")
    public Integer state;
    @ApiModelProperty(value = "认证状态")
    public Integer certifyState;

}
