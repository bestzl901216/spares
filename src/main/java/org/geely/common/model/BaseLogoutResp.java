package org.geely.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ricardo zhou
 */
@Data
public class BaseLogoutResp {

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
     * 用户信息
     */
    @ApiModelProperty(value = "数据")
    private String data;

    public boolean isSuccess() {
        return "200".equals(code);
    }
}
