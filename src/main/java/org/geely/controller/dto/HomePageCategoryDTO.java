package org.geely.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ricardo zhou
 */
@Data
public class HomePageCategoryDTO {
    @ApiModelProperty(value = "一级前台类目编号")
    public Integer c1Id;
    @ApiModelProperty(value = "类目名")
    public String c1Name;
    @ApiModelProperty(value = "类目编码")
    public String c1Code;
    @ApiModelProperty(value = "类目图片")
    public String imgUrl;
    @ApiModelProperty(value = "排序")
    public Integer sort;
    @ApiModelProperty(value = "SKU")
    public List<HomePageSkuDTO> homePageSkus;
}
