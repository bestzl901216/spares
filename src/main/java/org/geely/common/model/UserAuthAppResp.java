package org.geely.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Date 2023/10/11 09:31
 * @Author chaoming.zhu
 */
@Data
@ApiModel(value = "查询用户授权的应用resp")
public class UserAuthAppResp {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("类型 1-平台 2-微应用")
    private Integer applicationType;

    @ApiModelProperty("名称")
    private String applicationName;

    @ApiModelProperty("微服务所属平台id")
    private Long platformId;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("创建人ID")
    private String createId;

    @ApiModelProperty("创建人")
    private String createName;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新人ID")
    private String updateId;

    @ApiModelProperty("更新人")
    private String updateName;


    @ApiModelProperty("品牌")
    private String brand;

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("图标url")
    private String iconurl;

    @ApiModelProperty("应用id")
    private String purchaseId;

    @ApiModelProperty("应用主域名")
    private String appHost;

}