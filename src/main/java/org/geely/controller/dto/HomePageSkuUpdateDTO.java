package org.geely.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ricardo zhou
 */
@Data
public class HomePageSkuUpdateDTO {
    @ApiModelProperty(value="编号")
    public Integer id;
    @ApiModelProperty(value="商城编号")
    public Integer mallId;
    @ApiModelProperty(value = "类目编号")
    public Integer c1Id;
    @ApiModelProperty(value = "sku编号")
    public Integer skuId;
    @ApiModelProperty(value = "排序")
    public Integer sort;
    @ApiModelProperty(value = "版本")
    public Integer version;
}
