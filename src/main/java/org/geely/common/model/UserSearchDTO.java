package org.geely.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserSearchDTO {

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "显示名称")
    private String displayName;

    @ApiModelProperty(value = "userUuid")
    private String userUuid;

    @ApiModelProperty(value = "外部id")
    private String externalId;

    @ApiModelProperty(value = "组织名称")
    private String belong;

    @ApiModelProperty(value = "组织外部id")
    private String orgId;

    @ApiModelProperty(value = "组织uuid")
    private String ouUuid;
}
