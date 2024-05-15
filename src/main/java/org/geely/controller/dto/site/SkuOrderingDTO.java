package org.geely.controller.dto.site;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.geely.controller.dto.ProductTagDTO;
import org.geely.controller.dto.SkuSpecsDTO;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel("结算商品")
public class SkuOrderingDTO {
    @ApiModelProperty("商品id")
    private Integer productId;
    @ApiModelProperty("SKU_id")
    private Integer skuId;
    @ApiModelProperty("SKU编码")
    private String skuCode;
    @ApiModelProperty("商品名称")
    private String name;
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
    @ApiModelProperty("库存")
    private Integer stock;
    @ApiModelProperty("商品图片")
    private String image;
    @ApiModelProperty("价格")
    private BigDecimal price;
    @ApiModelProperty("单位")
    private String unit;
    @ApiModelProperty("最小起订量")
    private Integer moq;
    @ApiModelProperty("商品状态：-1已失效,0正常,1已下架")
    private Integer skuCartState;
    @ApiModelProperty("标签")
    private List<ProductTagDTO> tags;
    @ApiModelProperty("规格")
    private List<SkuSpecsDTO> specs;
    @ApiModelProperty("是否包邮")
    private boolean isFreeShipping;
}
