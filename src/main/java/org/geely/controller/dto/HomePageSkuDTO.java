package org.geely.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ricardo zhou
 */
@Data
public class HomePageSkuDTO {
    @ApiModelProperty(value = "sku编号")
    public Integer skuId;
    @ApiModelProperty(value = "sku图片")
    public String image;
    @ApiModelProperty(value = "商品名称")
    public String spuName;
    @ApiModelProperty(value = "商品描述")
    public String spuDescription;
    @ApiModelProperty(value = "商品标签")
    public List<ProductTagDTO> spuTags;
    @ApiModelProperty(value = "排序")
    public Integer sort;
    @ApiModelProperty(value = "spu编号")
    public Integer productId;
    @ApiModelProperty(value = "是否包邮")
    public Boolean freeShipping;
}
