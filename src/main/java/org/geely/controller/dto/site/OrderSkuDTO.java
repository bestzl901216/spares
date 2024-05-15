package org.geely.controller.dto.site;

import cn.hutool.core.lang.Assert;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.geely.common.enums.CartSkuEnum;
import org.geely.common.enums.OrderSubmitErrorEnum;
import org.geely.domain.core.data.SaleOrderItemData;

import java.math.BigDecimal;

@Data
public class OrderSkuDTO {
    @ApiModelProperty("店铺id")
    private Integer shopId;
    @ApiModelProperty("商家id")
    private Integer supplierId;
    @ApiModelProperty("商品id")
    private Integer productId;
    @ApiModelProperty("SKU_id")
    private Integer skuId;
    @ApiModelProperty("商品编码")
    private String productCode;
    @ApiModelProperty("SKU编码")
    private String skuCode;
    @ApiModelProperty("商品名称")
    private String name;
    @ApiModelProperty("品质")
    private String quality;
    @ApiModelProperty("品牌")
    private String brand;
    @ApiModelProperty("物料编码")
    private String materialCode;
    @ApiModelProperty("库存")
    private Integer stock;
    @ApiModelProperty("是否包邮")
    private Boolean isFreeShipping;
    @ApiModelProperty("商品图片")
    private String image;
    @ApiModelProperty("价格")
    private BigDecimal price;
    @ApiModelProperty("单位")
    private String unit;
    @ApiModelProperty("最小起订量")
    private Integer moq;
    @ApiModelProperty("商品状态")
    private CartSkuEnum skuCartState;
    @ApiModelProperty("规格")
    private String specs;

    private void check(Integer quantity) {
        Assert.isTrue(skuCartState.equals(CartSkuEnum.NORMAL), OrderSubmitErrorEnum.SKU_INVALID.getMessage());
        Assert.isTrue(quantity.compareTo(stock) <= 0 && quantity.compareTo(moq) >=0, OrderSubmitErrorEnum.STOCK_CHANGED.getMessage());
    }

    public SaleOrderItemData toOrderItem(Integer quantity) {
        check(quantity);
        SaleOrderItemData data = new SaleOrderItemData();
        data.setMaterialCode(materialCode);
        data.setOriginPrice(price);
        data.setPrice(price);
        data.setProductBrand(brand);
        data.setProductCode(productCode);
        data.setSkuCode(skuCode);
        data.setProductId(productId);
        data.setSkuId(skuId);
        data.setProductName(name);
        data.setProductQuality(quality);
        data.setQuantity(quantity);
        data.setSupportReturn(true);
        data.setUnit(unit);
        data.setIsFreeShipping(isFreeShipping);
        data.setImage(image);
        data.setSkuSpecs(specs);
        data.setDeliveryQuantity(0);
        data.setReceivedQuantity(0);
        return data;
    }
}
