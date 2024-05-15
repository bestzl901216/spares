package org.geely.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ricardo zhou
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HomePageSkuListDTO extends HomePageSkuDTO {
    @ApiModelProperty(value = "一级前台类目编号")
    public Integer c1Id;
    @ApiModelProperty(value = "类目图片")
    public String imgUrl;
    @ApiModelProperty(value = "类名")
    public String c1Name;
    @ApiModelProperty(value = "类Code")
    public String c1Code;
}
