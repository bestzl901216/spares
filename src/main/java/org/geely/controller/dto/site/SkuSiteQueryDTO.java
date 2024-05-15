package org.geely.controller.dto.site;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.geely.common.enums.SkuSortByEnum;
import org.geely.common.model.PageDTO;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

/**
 * @author cong huang
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("商品SKU查询")
public class SkuSiteQueryDTO extends PageDTO {
    @ApiModelProperty(hidden = true)
    private Integer mallId;
    @ApiModelProperty(hidden = true)
    private Integer customerId;
    @ApiModelProperty(hidden = true)
    private Set<Integer> categoryIds;
    @ApiModelProperty(value = "关键词")
    @Length(max = 100)
    private String keywords;
    @ApiModelProperty(value = "分类id")
    private Integer categoryId;
    @ApiModelProperty(value = "品牌id")
    private Integer brandId;
    @ApiModelProperty(value = "是否包邮")
    private Boolean freeShipping;
    @ApiModelProperty(value = "标签id,多个以逗号分隔")
    private String tagIds;
    @ApiModelProperty(hidden = true)
    private Set<Integer> tagIdSet;
    @ApiModelProperty(value = "排序字段")
    private SkuSortByEnum sortBy;
    @ApiModelProperty(value = "是否正序")
    private Boolean isAsc;
}
