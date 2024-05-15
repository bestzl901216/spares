package org.geely.controller.dto.site;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("结算页商品清单")
public class OrderingListDTO {
    @ApiModelProperty("店铺id")
    private Integer shopId;
    @ApiModelProperty("店铺名称")
    private String shopName;
    @ApiModelProperty("店铺状态：0禁用，1启用")
    private Integer shopState;
    List<SkuOrderingDTO> skus;
}
