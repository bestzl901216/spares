package org.geely.controller.dto.site;

import cn.hutool.core.lang.Assert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * @author cong huang
 */
@Data
@ApiModel("提交订单")
public class SaleOrderSubmitDTO {
    @ApiModelProperty(value = "收货地址id", required = true)
    @NotNull(message = "收货地址不能为空")
    private Integer addressId;
    @ApiModelProperty(value = "订单", required = true)
    @NotEmpty(message = "订单信息不能为空")
    @Valid
    private List<SaleOrderCreateDTO> saleOrders;
    @ApiModelProperty(value = "是否从购物车结算", required = true)
    @NotNull(message = "是否从购物车结算不能为空")
    private Boolean isFromCart;
    @ApiModelProperty(hidden = true)
    private Integer mallId;
    @ApiModelProperty(hidden = true)
    private Integer customerId;
    @ApiModelProperty(hidden = true)
    private Integer accountId;
    public Set<Integer> getSkuSet() {
        Set<Integer> skuSet = new HashSet<>();
        for (SaleOrderCreateDTO saleOrder : getSaleOrders()) {
            for (SaleOrderItemCreateDTO item : saleOrder.getItems()) {
                Assert.isFalse(skuSet.contains(item.getSkuId()), "重复的skuId");
                skuSet.add(item.getSkuId());
            }
        }
        return skuSet;
    }
    public Map<Integer, Integer>  getSkuQtyMap() {
        Map<Integer, Integer> skuMap = new HashMap<>();
        for (SaleOrderCreateDTO saleOrder : getSaleOrders()) {
            for (SaleOrderItemCreateDTO item : saleOrder.getItems()) {
                Assert.isFalse(skuMap.containsKey(item.getSkuId()), "重复的skuId");
                skuMap.put(item.getSkuId(), item.getQuantity());
            }
        }
        return skuMap;
    }
    public Set<Integer> getShopIds(){
        Set<Integer> shopIds = new HashSet<>();
        for (SaleOrderCreateDTO saleOrder : getSaleOrders()) {
            Assert.isFalse(shopIds.contains(saleOrder.getShopId()), "重复的shopId");
            shopIds.add(saleOrder.getShopId());
        }
        return shopIds;
    }

    public void baseCheck() {
        Assert.isFalse(saleOrders.isEmpty());
        for (SaleOrderCreateDTO saleOrder : saleOrders) {
            Assert.isFalse(saleOrder.getItems().isEmpty());
        }
    }
}

