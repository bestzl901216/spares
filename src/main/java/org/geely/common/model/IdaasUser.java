package org.geely.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IdaasUser {

    /**
     * sub : 3993326077301050171
     * ou_id : 7759147560545495208
     * nickname : 默认管理员
     * ou_name : 吉利0724
     * username : Geely_2020
     */
    @ApiModelProperty(name = "外部id")
    private String sub;

    @ApiModelProperty(name = "组织id")
    private String ou_id;

    @ApiModelProperty(name = "名称")
    private String nickname;

    @ApiModelProperty(name = "组织名称")
    private String ou_name;

    @ApiModelProperty(name = "用户名")
    private String username;
}
