package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author ricardo zhou
 */
@Data
@ApiModel("新建市场渠道")
public class MarketChannelCreateDTO {
    /**
     * 名字
     */
    @ApiModelProperty(value = "名称", required = true)
    @NotBlank(message = "名称不能为空")
    public String name;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    public String remark;
}
