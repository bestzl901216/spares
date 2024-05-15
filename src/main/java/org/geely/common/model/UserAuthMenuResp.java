package org.geely.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Date 2023/10/11 09:31
 * @Author chaoming.zhu
 */
@Data
@ApiModel(value = "查询平台授权的菜单")
public class UserAuthMenuResp {

    @ApiModelProperty("关联微应用id")
    private String microAppId;

    @ApiModelProperty("菜单名称")
    private String menuName;

    @ApiModelProperty("菜单链接")
    private String url;

    @ApiModelProperty("图标链接")
    private String imageUrl;

    @ApiModelProperty("菜单路径")
    private String path;

    @ApiModelProperty("排序值")
    private Integer sortValue;

    @ApiModelProperty("是否外链 0-否 1-是")
    private Integer isExternal;

    @ApiModelProperty("菜单权限值")
    private String permissionValue;

    @ApiModelProperty("是否隐藏 0-否 1-是")
    private Integer isHide;

    @ApiModelProperty("应用id")
    private String purchaseId;

}