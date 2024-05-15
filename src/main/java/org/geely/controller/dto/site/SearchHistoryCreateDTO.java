package org.geely.controller.dto.site;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author ricardo zhou
 */
@Data
@ApiModel("搜索历史创建DTO")
public class SearchHistoryCreateDTO {
    @NotBlank(message = "搜索词不能为空")
    @ApiModelProperty(value = "搜索词", required = true)
    private String keyword;
}
