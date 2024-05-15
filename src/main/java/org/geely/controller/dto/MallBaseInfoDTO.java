package org.geely.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ricardo zhou
 */
@Data
public class MallBaseInfoDTO {
    private Integer id;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "宣传标语")
    private String motto;
    @ApiModelProperty(value = "服务热线")
    private String servicePhone;
    @ApiModelProperty(value = "服务时间")
    private String serviceTime;
    @ApiModelProperty(value = "logo图片地址")
    private String logo;
    @ApiModelProperty(value = "版权")
    private String copyRight;
    @ApiModelProperty(value = "icp备案信息")
    private String filingsIcp;
    @ApiModelProperty(value = "icp备案跳转地址")
    private String filingsIcpUrl;
    @ApiModelProperty(value = "网安备案信息")
    private String filingsRecord;
    @ApiModelProperty(value = "icp备案跳转地址")
    private String filingsRecordUrl;
    @ApiModelProperty(value = "轮播图片地址")
    private List<String> urls;
}
