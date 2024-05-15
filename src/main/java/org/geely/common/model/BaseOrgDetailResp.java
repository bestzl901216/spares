package org.geely.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 67091
 */
@Data
public class BaseOrgDetailResp {

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
     * 组织信息
     */
    @ApiModelProperty(value = "数据")
    private OrgDetailDTO data;

    public boolean isSuccess() {
        return "200".equals(code);
    }
}
