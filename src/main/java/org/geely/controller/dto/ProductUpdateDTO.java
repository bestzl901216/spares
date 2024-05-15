package org.geely.controller.dto;

import cn.hutool.core.lang.Assert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author cong huang
 */
@Data
@ApiModel("商品")
public class ProductUpdateDTO {
    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "商品id不能为空")
    private int id;
    @ApiModelProperty(value = "前台类目id", required = true)
    @NotNull(message = "类目id不能为空")
    private Integer categoryId;
    @ApiModelProperty(value = "品质id")
    private Integer qualityId;
    @ApiModelProperty(value = "品牌id", required = true)
    @NotNull(message = "品牌id不能为空")
    private Integer brandId;
    @ApiModelProperty(value = "商品名称", required = true)
    @NotBlank(message = "商品名称不能为空")
    @Length(max = 50)
    private String name;
    @ApiModelProperty(value = "商品描述", required = true)
    @NotBlank(message = "商品描述不能为空")
    @Length(max = 200)
    private String description;
    @ApiModelProperty(value = "商品Sku", required = true)
    @NotEmpty(message = "SKU不能为空")
    @Valid
    private List<SkuUpdateDTO> skus;
    @ApiModelProperty(value = "适用车型ids")
    private Set<Integer> vehicleIds;
    @ApiModelProperty(value = "标签ids")
    private Set<Integer> tagIds;
    @ApiModelProperty(value = "商品规格")
    @Valid
    private List<ProductPropsUpdateDTO> props;
    @ApiModelProperty(value = "商品详情图片")
    private List<String> images;
    public void checkAndProcess(){
        if(images != null) {
            Set<String> imageSet = new HashSet<>(images);
            Assert.isTrue(imageSet.size() == images.size(), "包含重复的商品详情图片");
        }
        if(skus != null && !skus.isEmpty()) {
            Assert.isFalse(skus.size() > 100, "sku数量不能超过100");
            Set<String> specsKeys = skus.stream().map(SkuCreateDTO::getSpecsKey).collect(Collectors.toSet());
            Assert.isTrue(skus.size() == specsKeys.size(), "包含重复属性的SKU");
            skus.forEach(SkuCreateDTO::checkAndProcess);
            skus = skus.stream().filter(x->x.getMaterialId() != null && !x.getMaterialId().equals(0)).collect(Collectors.toList());
            AtomicInteger sort = new AtomicInteger(1);
            for (SkuUpdateDTO dto : skus) {
                dto.setSort(sort.getAndIncrement());
            }
        }
        List<SkuSpecsRelCreateDTO> allSpecs = new ArrayList<>();
        assert skus != null;
        skus.forEach(x-> allSpecs.addAll(x.getSpecs()));
        Map<Integer, Set<String>> specsMap = allSpecs.stream().collect(Collectors.groupingBy(SkuSpecsRelCreateDTO::getSpecsTypeId, Collectors.collectingAndThen(Collectors.toList(), g->g.stream().map(SkuSpecsRelCreateDTO::getSpecs).collect(Collectors.toSet()))));
        for (Integer x : specsMap.keySet()) {
            Assert.isFalse(specsMap.get(x).size() > 20, "属性值不能超过20种");
        }
    }
}
