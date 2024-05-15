package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ricardo zhou
 */
@Data
@ApiModel("店铺信息")
public class ShopSimpleDTO {
    @ApiModelProperty("店铺id")
    private Integer id;
    @ApiModelProperty("店铺名称")
    private String name;
}
