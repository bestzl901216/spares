package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.geely.domain.common.data.MarketChannelRelData;
import org.geely.domain.core.ProductImage;
import org.geely.domain.core.ProductProps;
import org.geely.domain.core.Sku;
import org.geely.domain.core.data.ProductTagData;

import java.util.List;

/**
 * @author cong huang
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("商品")
public class ProductSupplierDTO extends ProductShopDTO {
    @ApiModelProperty("店铺")
    private ShopSimpleDTO shop;
    public ProductSupplierDTO(ProductDTO dto, List<ProductTagData> tags, List<ProductImage> images, List<ProductProps> props, List<Sku> skus, List<MarketChannelRelData> marketChannels, ShopSimpleDTO shop){
        super(dto, tags, images, props, skus, marketChannels);
        this.shop = shop;
    }
}
