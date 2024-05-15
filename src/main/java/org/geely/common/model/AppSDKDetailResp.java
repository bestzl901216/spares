package org.geely.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Date 2023/12/12 09:26
 * @Author chaoming.zhu
 */
@Data
public class AppSDKDetailResp {

    @ApiModelProperty(value = "应用id")
    private String purchaseId;

    @ApiModelProperty(value = "应用名称")
    private String applicationName;

    @ApiModelProperty(value = "入口链接")
    private String path;

    @ApiModelProperty("应用类型 1-平台 2-微应用")
    private Integer applicationType;

    @ApiModelProperty(value = "重定向地址")
    private String webServerRedirectUri;

    @ApiModelProperty(value = "首页地址")
    private String homePageUrl;

    @ApiModelProperty(value = "应用icon url")
    private String iconURL;

    @ApiModelProperty(value = "应用uuid")
    private String uuid;

    @ApiModelProperty(value = "应用主域名")
    private String appHost;

}
