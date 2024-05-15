package org.geely.controller.dto;

import com.mysql.cj.util.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.geely.common.enums.ProductStateEnum;
import org.geely.domain.common.MarketChannelRel;
import org.geely.domain.common.utils.PriceUtil;
import org.geely.domain.core.data.SkuPriceData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author cong huang
 */
@Data
@ApiModel("商品")
public class ProductShopListDTO {
    @ApiModelProperty("商品id")
    private Integer id;
    @ApiModelProperty("商品编码")
    private String code;
    @ApiModelProperty("名称")
    private String name;
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
    @ApiModelProperty("SKU数量")
    private Integer skuSize;
    @ApiModelProperty("总库存")
    private Integer stock;
    @ApiModelProperty("状态：-1审核失败, 0待审核,1下架,2上架")
    private ProductStateEnum state;
    @ApiModelProperty("详情图片")
    private String image;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("最低价格")
    private BigDecimal minPrice;
    @ApiModelProperty("最高价格")
    private BigDecimal maxPrice;

    public void buildSkuInfo(List<SkuBuilderDTO> skuList, Map<Integer, List<SkuPriceData>> skuPriceMap, List<MarketChannelRel> channels) {
        if (skuList == null || skuList.isEmpty()) {
            skuSize = 0;
            stock = 0;
            return;
        }
        int stockTotal = 0;
        List<BigDecimal> prices = new ArrayList<>();
        for (SkuBuilderDTO sku : skuList) {
            stockTotal += sku.getStock();
            if (channels != null && !channels.isEmpty()) {
                //计算渠道价格
                Map<Integer, BigDecimal> channelPriceMap = skuPriceMap.getOrDefault(sku.getId(), new ArrayList<>()).stream().collect(Collectors.toMap(SkuPriceData::getMarketChannelId, SkuPriceData::getPrice));
                for (MarketChannelRel channel : channels) {
                    Integer channelId = channel.getMarketChannelId();
                    if (channelPriceMap.containsKey(channelId)) {
                        prices.add(channelPriceMap.get(channelId));
                    } else {
                        BigDecimal skuPrice = PriceUtil.getMarkupPrice(sku.getPrice(), channel.getMarkupRate());
                        prices.add(skuPrice);
                    }
                }
            }
        }
        String skuImage = "";
        for (SkuBuilderDTO skuBuilderDTO : skuList) {
            if (!StringUtils.isNullOrEmpty(skuBuilderDTO.getImage())) {
                skuImage = skuBuilderDTO.getImage();
                break;
            }
        }
        image = skuImage;
        stock = stockTotal;
        skuSize = skuList.size();
        if (!prices.isEmpty()) {
            prices = prices.stream().sorted().collect(Collectors.toList());
            minPrice = prices.get(0);
            maxPrice = prices.get(prices.size() - 1);
        }
    }
}
