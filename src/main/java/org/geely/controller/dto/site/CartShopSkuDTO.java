package org.geely.controller.dto.site;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.geely.common.enums.EnableStateEnum;

@EqualsAndHashCode(callSuper = true)
@Data
public class CartShopSkuDTO extends CartSkuDTO {
    @ApiModelProperty("店铺id")
    private Integer shopId;
    @ApiModelProperty("店铺名称")
    private String shopName;
    @ApiModelProperty("店铺状态")
    private EnableStateEnum shopState;
}
