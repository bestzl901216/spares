package org.geely.controller.dto.site;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ricardo zhou
 */
@Data
@ApiModel("搜索历史")
public class SearchHistoryDTO {
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("关键字")
    private String keyword;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("更新时间")
    private String updateTime;
}
