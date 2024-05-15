package org.geely.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseUserHasPermissionResp {

    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码")
    private String code;

    /**
     * 信息
     */
    @ApiModelProperty(value = "信息")
    private String message;

    /**
     * 是否有权限
     */
    @ApiModelProperty(value = "数据")
    private Boolean data;

    public boolean isSuccess() {
        return "200".equals(code);
    }
}
