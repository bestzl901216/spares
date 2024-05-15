package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cong huang
 */
@Data
@ApiModel("商品标签")
public class ProductTagDTO {
    @ApiModelProperty("标签id")
    private Integer id;
    @ApiModelProperty("标签名称")
    private String name;
    @ApiModelProperty("字体颜色")
    private String fontColor;
    @ApiModelProperty("背景颜色")
    private String backgroundColor;
    @ApiModelProperty("spu编号")
    private Integer productId;
}
