package org.geely.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BaseMenuResp {

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
     * 菜单信息
     */
    @ApiModelProperty(value = "数据")
    private List<MenuDTO> data;

    public boolean isSuccess() {
        return "200".equals(code);
    }
}
