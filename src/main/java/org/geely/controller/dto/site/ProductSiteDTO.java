package org.geely.controller.dto.site;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.geely.controller.dto.*;
import org.geely.domain.common.data.MarketChannelRelData;
import org.geely.domain.core.ProductImage;
import org.geely.domain.core.ProductProps;
import org.geely.domain.core.Sku;
import org.geely.domain.core.data.ProductTagData;
import org.geely.infrastructure.db.convert.SupportDomainDtoConvert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cong huang
 */
@Data
@ApiModel("商品")
public class ProductSiteDTO {
    @ApiModelProperty("基本信息")
    private ProductDTO baseData;
    @ApiModelProperty("标签")
    private List<ProductTagDTO> tags;
    @ApiModelProperty("属性")
    private List<ProductPropsDTO> props;
    @ApiModelProperty("详情图片")
    private List<String> images;
    @ApiModelProperty("SKU")
    private List<SkuSiteDTO> skus;
    @ApiModelProperty("店铺")
    private ShopSimpleDTO shop;
    @ApiModelProperty("是否有效")
    private Boolean valid;

    public ProductSiteDTO() {
    }

    public static ProductSiteDTO invalidInstance() {
        ProductSiteDTO dto = new ProductSiteDTO();
        dto.valid = false;
        return dto;
    }

    public ProductSiteDTO(ProductDTO dto, List<ProductTagData> tags, List<ProductImage> images, List<ProductProps> props, List<Sku> skus, MarketChannelRelData marketChannel, ShopSimpleDTO shopDto) {
        baseData = dto;
        if (images != null && !images.isEmpty()) {
            this.images = images.stream().map(ProductImage::getUrl).collect(Collectors.toList());
        } else {
            this.images = new ArrayList<>();
        }
        if (props != null && !props.isEmpty()) {
            this.props = props.stream().map(x -> SupportDomainDtoConvert.INSTANCE.convert(x.getData())).collect(Collectors.toList());
        } else {
            this.props = new ArrayList<>();
        }
        if (tags != null && !tags.isEmpty()) {
            this.tags = tags.stream().map(SupportDomainDtoConvert.INSTANCE::convert).collect(Collectors.toList());
        } else {
            this.tags = new ArrayList<>();
        }
        this.skus = skus.stream().map(x -> x.getSiteDTO(marketChannel)).collect(Collectors.toList());
        this.shop = shopDto;
        this.valid = true;
    }
}
