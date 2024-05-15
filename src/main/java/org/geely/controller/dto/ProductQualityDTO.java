package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author ricardo zhou
 */
@Data
@ApiModel("商品标签")
public class ProductQualityDTO {
    private Integer id;
    private String name;
    private String remark;
    private String createTime;
}
