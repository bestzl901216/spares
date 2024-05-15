package org.geely.controller.site;

import cn.hutool.core.lang.Assert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.geely.common.constants.MvcConstant;
import org.geely.common.model.ResultJson;
import org.geely.common.utils.OperatorUtil;
import org.geely.controller.dto.site.*;
import org.geely.domain.core.Cart;
import org.geely.domain.core.data.CartData;
import org.geely.infrastructure.db.convert.SiteDtoConvert;
import org.geely.infrastructure.db.gateway.ProductDbGateway;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author cong huang
 */
@Api(value = "商城端-购物车接口", tags = "商城端-购物车接口")
@Slf4j
@RestController
@RequestMapping(MvcConstant.SITE)
public class CartController {
    @Resource
    private ProductDbGateway productDbGateway;

    @ApiOperation("加入购物车")
    @PostMapping("cart")
    public ResultJson<Integer> addCart(@Validated @RequestBody CartAddDTO dto) {
        Set<Integer> skuIds = new HashSet<>();
        skuIds.add(dto.getSkuId());
        List<SkuShopOrderingDTO> skus = productDbGateway.orderingSkuList(OperatorUtil.getMallId(), OperatorUtil.getPlatformId(), skuIds);
        Assert.isFalse(skus.isEmpty(), "skuId不存在");
        SkuShopOrderingDTO sku = skus.get(0);
        Assert.isTrue(sku.getSkuCartState().equals(0), "无效的SKU");
        Assert.isFalse(dto.getQuantity().compareTo(sku.getStock()) > 0, "库存不足");
        Assert.isFalse(dto.getQuantity().compareTo(sku.getMoq()) < 0, "添加数量必须大于起订量");
        CartData data = new CartData();
        data.setMallId(OperatorUtil.getMallId());
        data.setCustomerId(OperatorUtil.getPlatformId());
        data.setAccountId(OperatorUtil.getAccountId());
        data.setSkuId(dto.getSkuId());
        data.setQuantity(dto.getQuantity());
        Cart instance = Cart.add(data);
        return ResultJson.success(instance.getId());
    }

    @ApiOperation("修改数量")
    @PutMapping("cart/quantity")
    public ResultJson<Integer> updateQuantity(@Validated @RequestBody CartQtyUpdateDTO dto) {
        Cart cart = Cart.instanceOf(dto.getId());
        cart.validCheck(OperatorUtil.getMallId(), OperatorUtil.getPlatformId(), OperatorUtil.getAccountId());
        cart.updateQuantity(dto.getQuantity()).save();
        return ResultJson.success(cart.getId());
    }

    @ApiOperation("购物车数量")
    @GetMapping("cart/count")
    public ResultJson<Long> count() {
        return ResultJson.success(Cart.count(OperatorUtil.getMallId(), OperatorUtil.getPlatformId(), OperatorUtil.getAccountId()));
    }

    @ApiOperation("批量删除")
    @PostMapping("cart/delete")
    @Transactional(rollbackFor = Exception.class)
    public ResultJson<Boolean> delete(@Validated @RequestBody @ApiParam(required = true) Set<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return ResultJson.success(false);
        }
        List<Cart> carts = getValidCarts(ids);
        carts.forEach(Cart::delete);
        return ResultJson.success(true);
    }

    @ApiOperation("批量选择")
    @PutMapping("cart/selected")
    @Transactional(rollbackFor = Exception.class)
    public ResultJson<Boolean> selected(@Valid @RequestBody @ApiParam(required = true) @NotEmpty List<CartSelectDTO> params) {
        Assert.isTrue(params.stream().map(CartSelectDTO::getId).collect(Collectors.toSet()).size() == params.size(), "包含重复的购物车id");
        Map<Integer, Boolean> selectedMap = params.stream().collect(Collectors.toMap(CartSelectDTO::getId, CartSelectDTO::getSelected));
        List<Cart> carts = getValidCarts(selectedMap.keySet());
        carts.forEach(x -> x.selected(selectedMap.get(x.getId())).save());
        return ResultJson.success(true);
    }

    @ApiOperation("购物车列表")
    @GetMapping("cart/list")
    public ResultJson<List<CartListDTO>> list() {
        List<CartShopSkuDTO> cartShopSkuDTOList = productDbGateway.cartList(OperatorUtil.getMallId(), OperatorUtil.getPlatformId(), OperatorUtil.getAccountId());
        if (cartShopSkuDTOList.isEmpty()) {
            return ResultJson.success(new ArrayList<>());
        }
        //按店铺分组显示
        List<CartListDTO> result = new ArrayList<>();
        Map<Integer, List<CartShopSkuDTO>> shopMap = cartShopSkuDTOList.stream().collect(Collectors.groupingBy(CartShopSkuDTO::getShopId));
        shopMap.forEach((shopId, cartShopSkus) -> {
            CartListDTO dto = new CartListDTO();
            CartShopSkuDTO shop = cartShopSkus.get(0);
            dto.setShopId(shopId);
            dto.setShopName(shop.getShopName());
            dto.setShopState(shop.getShopState().getId());
            List<CartSkuDTO> skuDTOList = cartShopSkus.stream().map(SiteDtoConvert.INSTANCE::convert).collect(Collectors.toList());
            dto.setSkus(skuDTOList);
            result.add(dto);
        });
        return ResultJson.success(result);
    }

    private List<Cart> getValidCarts(Set<Integer> ids) {
        Assert.isFalse(ids.size() > 999, "选择的项目太多");
        List<Cart> carts = Cart.instancesOf(ids);
        Integer mallId = OperatorUtil.getMallId();
        Integer customerId = OperatorUtil.getPlatformId();
        Integer accountId = OperatorUtil.getAccountId();
        carts.forEach(x -> x.validCheck(mallId, customerId, accountId));
        return carts;
    }
}
