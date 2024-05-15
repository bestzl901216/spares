package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author ricardo zhou
 */
@Data
@ApiModel("渠道")
public class MarketChannelDTO {
    private Integer id;
    private String name;
    private String remark;
    private Integer customerCount;
    private String createTime;
}
