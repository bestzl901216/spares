package org.geely.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author cong huang
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("商品")
public class ProductSupplierListDTO extends ProductShopListDTO {
    @ApiModelProperty("店铺id")
    private Integer shopId;
    @ApiModelProperty("店铺名称")
    private String shopName;

}
