package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.geely.common.enums.PlatformTypeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ricardo zhou
 */
@Data
@ApiModel("平台切换")
public class PlatformSwitchDTO {
    @ApiModelProperty(value = "平台编码")
    private String code;

    @ApiModelProperty(value = "平台列表")
    private List<PlatformNameDTO> children;
    public PlatformSwitchDTO(PlatformTypeEnum typeEnum) {
        this.code = typeEnum.toString();
        this.children = new ArrayList<>();
    }
}
