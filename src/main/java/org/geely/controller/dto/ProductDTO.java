package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cong huang
 */
@Data
@ApiModel("商品")
public class ProductDTO {
    @ApiModelProperty("商品id")
    private Integer id;
    @ApiModelProperty("商品编码")
    private String code;
    @ApiModelProperty("前台类目id")
    private Integer categoryId;
    @ApiModelProperty("前台类目")
    private String category;
    @ApiModelProperty("品质id")
    private Integer qualityId;
    @ApiModelProperty("品质")
    private String quality;
    @ApiModelProperty("品牌id")
    private Integer brandId;
    @ApiModelProperty("品牌")
    private String brand;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("描述")
    private String description;
    @ApiModelProperty("状态：1下架,2上架")
    private Integer state;
    @ApiModelProperty("排序")
    private Integer sort;
}
