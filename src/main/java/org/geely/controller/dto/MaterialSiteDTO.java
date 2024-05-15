package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ricardo zhou
 */
@Data
@ApiModel("物料")
public class MaterialSiteDTO {
    @ApiModelProperty(value = "物料id")
    private Integer id;
    @ApiModelProperty(value = "物料编码")
    private String code;
    @ApiModelProperty(value = "物料名称")
    private String name;
    @ApiModelProperty(value = "oe编码")
    private String oeNo;
    @ApiModelProperty(value = "后台类目")
    private String category;
    @ApiModelProperty(value = "单位")
    private String unit;
}
