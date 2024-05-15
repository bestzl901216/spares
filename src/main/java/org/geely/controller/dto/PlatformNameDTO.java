package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 67091
 */
@Data
@ApiModel("平台信息")
public class PlatformNameDTO {
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "商家名称")
    private String supplierName;
    @ApiModelProperty(value = "租户名称")
    private String mallName;
    public PlatformNameDTO(Integer id) {
        this.id = id;
    }
}
