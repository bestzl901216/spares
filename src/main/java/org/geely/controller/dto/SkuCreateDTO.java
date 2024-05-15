package org.geely.controller.dto;

import cn.hutool.core.lang.Assert;
import com.google.gson.Gson;
import com.mysql.cj.util.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.geely.common.enums.EnableStateEnum;
import org.geely.domain.core.data.SkuData;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Data
@ApiModel("SKU")
public class SkuCreateDTO
{
    @ApiModelProperty(value = "规格",required = true)
    @NotEmpty(message = "规格不能为空")
    @Valid
    private List<SkuSpecsRelCreateDTO> specs;
    @ApiModelProperty(value = "单位",required = true)
    private String unit;
    @ApiModelProperty(value = "库存",required = true)
    private Integer stock;
    @ApiModelProperty(value = "是否包邮",required = true)
    private Boolean isFreeShipping;
    @ApiModelProperty(value = "起订量",required = true)
    private Integer moq;
    @ApiModelProperty(value = "物料id",required = true)
    private Integer materialId;
    @ApiModelProperty(value = "SKU图片")
    private List<String> images;
    @ApiModelProperty(value = "渠道固定价格")
    private List<SkuPriceCreateDTO> channelPrices;
    @ApiModelProperty(value = "状态",required = true)
    private EnableStateEnum state;
    @ApiModelProperty(hidden = true)
    private Integer sort;

    public SkuData toData() {
        SkuData data = new SkuData();
        data.setStock(stock);
        data.setMoq(moq);
        data.setMaterialId(materialId);
        data.setIsFreeShipping(isFreeShipping);
        data.setUnit(unit);
        data.setSort(sort);
        data.setState(state);
        return data;
    }

    public String getSpecsKey() {
        List<SkuSpecsRelCreateDTO> copyOfList = new ArrayList<>(specs);
        copyOfList.sort(Comparator.comparing(SkuSpecsRelCreateDTO::getSpecsTypeId));
        Gson gson = new Gson();
        return gson.toJson(copyOfList);
    }
    public void checkChannels(Set<Integer> channelIds){
        Assert.isTrue(this.getChannelPrices() == null || this.getChannelPrices().isEmpty() || channelIds.containsAll(this.getChannelPrices().stream().map(SkuPriceCreateDTO::getMarketChannelId).collect(Collectors.toList())), "包含不正确的渠道id");
    }
    public void checkAndProcess(){
        if(channelPrices != null && !channelPrices.isEmpty()) {
            channelPrices = channelPrices.stream().filter(x->x.getMarketChannelId() != null && x.getPrice() != null && x.getPrice().compareTo(BigDecimal.ZERO) > 0).collect(Collectors.toList());
        }
        if(state == null) {
            state = EnableStateEnum.ENABLE;
        }
        if(state != EnableStateEnum.ENABLE) {
            return;
        }
        Assert.isFalse(StringUtils.isNullOrEmpty(unit), "单位不能为空");
        Assert.isFalse(unit.length() > 10, "单位长度不能大于10");

        Assert.notNull(stock, "库存不能为空");
        Assert.isFalse(stock < 0, "库存不能小于0");

        Assert.notNull(isFreeShipping, "是否包邮不能为空");

        Assert.notNull(moq, "起订量不能为空");
        Assert.isFalse(moq < 1, "起订量不能小于1");

        Assert.notNull(materialId, "物料不能为空");

        Set<String> imageSet = new HashSet<>(images);
        Assert.isTrue(imageSet.size() == images.size(), "包含重复的SKU图片");

        Assert.isFalse(specs.size() > 5, "属性类型不能大于5种");

        Assert.isTrue(specs.stream().map(SkuSpecsRelCreateDTO::getSpecsTypeId).collect(Collectors.toSet()).size() == specs.size(), "SKU包含了重复的属性类型");
    }
}
