package org.geely.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MenuDTO {

    @ApiModelProperty(value = "菜单ID")
    private Long id;

    @ApiModelProperty("所属平台id")
    private Long applicationId;

    @ApiModelProperty("所属名称")
    private String applicationName;

    @ApiModelProperty("类型 1-顶部功能 2-顶部导航 3-侧边菜单")
    private Integer menuType;

    @ApiModelProperty("菜单名称")
    private String menuName;

    @ApiModelProperty("菜单链接")
    private String url;

    @ApiModelProperty("图标链接")
    private String imageUrl;

    @ApiModelProperty("上级菜单")
    private Long[] parentIds;

    @ApiModelProperty("菜单路径")
    private String path;

    @ApiModelProperty("排序值")
    private Integer sortValue;

    @ApiModelProperty("是否外链 0-否 1-是")
    private Integer isExternal;

    @ApiModelProperty("是否启用,1-启用，2-禁用")
    private Integer status;

    @ApiModelProperty("菜单权限值")
    private String permissionValue;

    @ApiModelProperty("应用id")
    private String purchaseId;
}
