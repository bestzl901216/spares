package org.geely.controller.dto.site;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.geely.controller.dto.ProductTagDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author cong huang
 */
@Data
@ApiModel("商品SKU")
public class SkuListDTO {
    @ApiModelProperty("商品id")
    private Integer productId;
    @ApiModelProperty("SKU_id")
    private Integer skuId;
    @ApiModelProperty("SKU编码")
    private String skuCode;
    @ApiModelProperty("商品名称")
    private String name;
    @ApiModelProperty("商品描述")
    private String description;
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
    @ApiModelProperty("物料编码")
    private String materialCode;
    @ApiModelProperty("是否包邮")
    private Boolean isFreeShipping;
    @ApiModelProperty("库存")
    private Integer stock;
    @ApiModelProperty("商品图片")
    private String image;
    @ApiModelProperty("价格")
    private BigDecimal price;
    @ApiModelProperty("店铺id")
    private Integer shopId;
    @ApiModelProperty("店铺名称")
    private String shopName;
    @ApiModelProperty("单位")
    private String unit;
    @ApiModelProperty("最小起订量")
    private Integer moq;
    @ApiModelProperty("标签")
    private List<ProductTagDTO> tags;
}

