package org.geely.controller.dto.site;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author ricardo zhou
 */
@Data
@ApiModel("订单追踪记录")
public class SaleOrderFlowDTO {
    private String roleName;
    private String content;
    private List<String> images;
    private String createTime;
}