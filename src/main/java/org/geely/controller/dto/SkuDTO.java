package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.geely.common.enums.EnableStateEnum;
import org.geely.domain.core.SkuImage;
import org.geely.domain.core.data.MaterialData;
import org.geely.domain.core.data.SkuPriceData;
import org.geely.domain.core.data.SkuSpecsData;
import org.geely.infrastructure.db.convert.CoreDomainDtoConvert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ApiModel("SKU")
public class SkuDTO {
    @ApiModelProperty(value = "SKUid")
    private Integer id;
    @ApiModelProperty(value = "SKU编码")
    private String code;
    @ApiModelProperty(value = "单位")
    private String unit;
    @ApiModelProperty(value = "库存")
    private Integer stock;
    @ApiModelProperty(value = "是否包邮")
    private Boolean isFreeShipping;
    @ApiModelProperty(value = "最小起订量")
    public Integer moq;
    @ApiModelProperty(value = "状态：0禁用，1启用")
    private EnableStateEnum state;
    @ApiModelProperty(value = "物料信息")
    private MaterialDTO material;
    @ApiModelProperty(value = "图片")
    private List<String> images;
    @ApiModelProperty(value = "规格")
    private List<SkuSpecsDTO> specsList;
    @ApiModelProperty(value = "渠道固定价格")
    private List<SkuPriceDTO> priceList;

    public void setRelations(MaterialData material, List<SkuImage> images, List<SkuSpecsData> skuSpecsList, List<SkuPriceData> skuPriceList) {
        this.material = CoreDomainDtoConvert.INSTANCE.convert(material);
        if (images != null) {
            this.images = images.stream().map(SkuImage::getUrl).collect(Collectors.toList());
        } else {
            this.images = new ArrayList<>();
        }
        if (skuSpecsList != null) {
            this.specsList = skuSpecsList.stream().map(CoreDomainDtoConvert.INSTANCE::convert).collect(Collectors.toList());
        } else {
            this.specsList = new ArrayList<>();
        }
        if (skuPriceList != null) {
            this.priceList = skuPriceList.stream().map(CoreDomainDtoConvert.INSTANCE::convert).collect(Collectors.toList());
        } else {
            this.priceList = new ArrayList<>();
        }
    }
}
