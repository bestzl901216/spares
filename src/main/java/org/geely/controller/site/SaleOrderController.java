package org.geely.controller.site;

import cn.hutool.core.lang.Assert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.geely.common.constants.MvcConstant;
import org.geely.common.enums.*;
import org.geely.common.model.ResultJson;
import org.geely.common.utils.OperatorUtil;
import org.geely.controller.dto.site.*;
import org.geely.domain.common.MallBankAccount;
import org.geely.domain.common.MarketChannelRel;
import org.geely.domain.core.*;
import org.geely.domain.core.data.SaleOrderPayApproveData;
import org.geely.domain.support.SaleOrderFlow;
import org.geely.infrastructure.db.convert.SupportDomainDtoConvert;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author cong huang
 */
@Api(value = "商城端-订单接口", tags = "商城端-订单接口")
@Slf4j
@RestController
@RequestMapping(MvcConstant.SITE)
public class SaleOrderController {
    @ApiOperation("提交订单")
    @PostMapping("order")
    @Transactional(rollbackFor = Exception.class)
    public ResultJson<OrderSubmitResultDTO> submit(@Valid @RequestBody SaleOrderSubmitDTO dto) {
        //参数预处理
        dto.setCustomerId(OperatorUtil.getPlatformId());
        dto.setMallId(OperatorUtil.getMallId());
        dto.setAccountId(OperatorUtil.getAccountId());
        //生成订单
        List<SaleOrder> newOrders;
        try {
            newOrders = SaleOrder.submit(dto);
        }
        catch (Exception ex) {
            //返回订单异常信息
            OrderSubmitErrorEnum errorEnum = OrderSubmitErrorEnum.of(ex.getMessage());
            if (errorEnum.equals(OrderSubmitErrorEnum.UNKNOWN)) {
                throw ex;
            } else {
                return ResultJson.success(OrderSubmitResultDTO.fail(errorEnum));
            }
        }
        //合并支付
        Integer parentOrderPayId = 0;
        String orderPaySn = "";
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (SaleOrder newOrder : newOrders) {
            totalAmount = totalAmount.add(newOrder.getAmount());
        }
        //如有多个订单，创建合并支付单
        if(newOrders.size() > 1) {
            SaleOrderPay parentOrderPay = SaleOrderPay.newParentInstance(totalAmount);
            parentOrderPayId = parentOrderPay.getId();
            orderPaySn  = parentOrderPay.getSn();
        }
        for (SaleOrder newOrder : newOrders) {
            //每个订单都有支付单，用于商家订单列表以支付单分页显示
            SaleOrderPay childPay = SaleOrderPay.newChildInstance(parentOrderPayId, newOrder.getId(), newOrder.getAmount());
            if(newOrders.size() == 1) {
                orderPaySn = childPay.getSn();
            }
            //添加订单追踪记录
            SaleOrderFlow.newConfirmRecord(newOrder.getId());
        }
        //更新库存
        Map<Integer, Integer> skuQtyMap = dto.getSkuQtyMap();
        List<Sku> skus = Sku.instancesOf(skuQtyMap.keySet());
        skus.forEach(x->x.lockStock(skuQtyMap.get(x.getId())));
        //从购物车中删除
        if(Boolean.TRUE.equals(dto.getIsFromCart())) {
            List<Cart> carts = Cart.instancesOf(dto.getMallId(),dto.getCustomerId(),dto.getAccountId(),dto.getSkuSet());
            carts.forEach(Cart::delete);
        }
        return ResultJson.success(OrderSubmitResultDTO.success(orderPaySn));
    }

    @ApiOperation("收银台")
    @GetMapping("order/checkout")
    public ResultJson<CheckOutDTO> checkOut(@Validated @ApiParam(name = "sn", value = "支付单号", required = true) @NotBlank String sn){
        //支付单
        SaleOrderPay orderPay = SaleOrderPay.instanceOf(sn);
        Assert.isTrue(orderPay.getState().equals(OrderPayStateEnum.TO_BE_PAID), "不正确的支付状态");
        //订单
        List<SaleOrder> orders = SaleOrder.instancesOf(orderPay.getSaleOrderIds());
        Customer customer = Customer.instanceOf(OperatorUtil.getPlatformId());
        Assert.isTrue(orders.stream().allMatch(x->x.getCustomerId().equals(customer.getId()) && x.getState().equals(OrderStateEnum.TO_BE_PAID)), "无效的订单数据");
        //店铺
        Set<Integer> shopIds = orders.stream().map(SaleOrder::getShopId).collect(Collectors.toSet());
        List<Shop> shops = Shop.instancesOf(shopIds);
        Assert.isTrue(shops.stream().allMatch(x->x.getState().equals(EnableStateEnum.ENABLE)), "包含无效的店铺");
        Assert.isTrue(MarketChannelRel.matchAll(PlatformTypeEnum.SHOP, shopIds, customer.getMarketChannelId()), "包含无效的店铺");
        //商家
        List<Supplier> suppliers = Supplier.instancesOf(shops.stream().map(Shop::getSupplierId).collect(Collectors.toSet()));
        Assert.isTrue(suppliers.stream().allMatch(x->x.getState().equals(EnableStateEnum.ENABLE)), "包含无效的商家");
        Map<Integer, MallBankAccount> bankAccountMap = MallBankAccount.instancesOf(suppliers.stream().map(Supplier::getBankAccountId).collect(Collectors.toSet())).stream().collect(Collectors.toMap(MallBankAccount::getId, v->v));
        //收银台
        CheckOutDTO result = new CheckOutDTO();
        result.setSn(sn);
        Optional<LocalDateTime> deadline = orders.stream().map(SaleOrder::getPayDeadline).min(Comparator.comparing(x->x));
        Assert.isTrue(deadline.isPresent() && LocalDateTime.now().isBefore(deadline.get()), "无效的订单数据");
        deadline.ifPresent(x-> result.setDeadline(x.toString()));
        //支付类型
        result.setPayTypes(new ArrayList<>());
        result.getPayTypes().add(PayTypeEnum.ENTERPRISE_OFFLINE);
        result.setAmount(BigDecimal.valueOf(orders.stream().mapToDouble(x->x.getAmount().doubleValue()).sum()));
        result.setSuppliers(new ArrayList<>());
        Map<Integer, List<Shop>> supplierShopMap = shops.stream().collect(Collectors.groupingBy(Shop::getSupplierId));
        Map<Integer, List<SaleOrder>> shopOrderMap = orders.stream().collect(Collectors.groupingBy(SaleOrder::getShopId));
        //商家分组显示
        for (Supplier supplier : suppliers) {
            CheckOutSupplierDTO supplierDTO = new CheckOutSupplierDTO();
            supplierDTO.setId(supplier.getId());
            supplierDTO.setName(supplier.getName());
            supplierDTO.setAmount(BigDecimal.valueOf(orders.stream().filter(x->x.getSupplerId().equals(supplier.getId())).mapToDouble(x->x.getAmount().doubleValue()).sum()));
            supplierDTO.setBankAccount(SupportDomainDtoConvert.INSTANCE.convert(bankAccountMap.get(supplier.getBankAccountId()).getData()));
            supplierDTO.setShops(new ArrayList<>());
            List<Shop> shopList = supplierShopMap.get(supplier.getId());
            //店铺分组显示
            for (Shop shop : shopList) {
                CheckOutShopDTO shopDTO = new CheckOutShopDTO();
                List<SaleOrder> orderList = shopOrderMap.get(shop.getId());
                shopDTO.setId(shop.getId());
                shopDTO.setName(shop.getName());
                shopDTO.setAmount(BigDecimal.valueOf(orderList.stream().mapToDouble(x -> x.getAmount().doubleValue()).sum()));
                shopDTO.setOrders(orderList.stream().map(x -> new CheckOutOrderDTO(x.getId(), x.getSn(), x.getAmount())).collect(Collectors.toList()));
                supplierDTO.getShops().add(shopDTO);
            }
            result.getSuppliers().add(supplierDTO);
        }
        return ResultJson.success(result);
    }
    @ApiOperation("线下付款")
    @PostMapping("order/pay/offline")
    @Transactional(rollbackFor = Exception.class)
    public ResultJson<Boolean> payOffline(@Valid @RequestBody OrderPayOfflineDTO param){
        param.check();
        //支付单
        SaleOrderPay orderPay = SaleOrderPay.instanceOf(param.getSn());
        Assert.isTrue(orderPay.getState().equals(OrderPayStateEnum.TO_BE_PAID), "不正确的支付状态");
        //订单
        List<SaleOrder> orders = SaleOrder.instancesOf(orderPay.getSaleOrderIds());
        Customer customer = Customer.instanceOf(OperatorUtil.getPlatformId());
        Assert.isTrue(orders.stream().allMatch(x->x.getCustomerId().equals(customer.getId()) && x.getState().equals(OrderStateEnum.TO_BE_PAID)), "不正确的订单状态");
        //店铺
        Set<Integer> shopIds = orders.stream().map(SaleOrder::getShopId).collect(Collectors.toSet());
        List<Shop> shops = Shop.instancesOf(shopIds);
        Assert.isTrue(shops.stream().allMatch(x->x.getState().equals(EnableStateEnum.ENABLE)), "包含无效的店铺");
        Assert.isTrue(MarketChannelRel.matchAll(PlatformTypeEnum.SHOP, shopIds, customer.getMarketChannelId()), "包含无效的店铺");
        //商家
        Set<Integer> supplierIds = shops.stream().map(Shop::getSupplierId).collect(Collectors.toSet());
        List<Supplier> suppliers = Supplier.instancesOf(supplierIds);
        Assert.isTrue(suppliers.stream().allMatch(x->x.getState().equals(EnableStateEnum.ENABLE)), "包含无效的商家");
        //创建支付凭证
        for (OrderPaySupplierApproveDTO dto : param.getSupplierVouchers()) {
            Assert.isTrue(supplierIds.contains(dto.getSupplierId()), "包含无效的商家id");
            BigDecimal supplierAmount = BigDecimal.valueOf(orders.stream().filter(x->x.getSupplerId().equals(dto.getSupplierId())).mapToDouble(x->x.getAmount().doubleValue()).sum());
            SaleOrderPayApproveData data = new SaleOrderPayApproveData(orderPay.getId(), supplierAmount, dto);
            SaleOrderPayApprove.newInstance(data);
        }
        //更改支付单状态
        orderPay.pendingApproval().save();
        //更改订单支付状态
        Map<Integer, String> vouchersMap = param.getSupplierVouchers().stream().collect(Collectors.toMap(OrderPaySupplierApproveDTO::getSupplierId, OrderPaySupplierApproveDTO::getVouchers));
        for (SaleOrder order : orders) {
            order.toBeApproved().save();
            SaleOrderFlow.newOfflinePayRecord(order.getId(), orderPay.getSn(), Arrays.stream(vouchersMap.get(order.getSupplerId()).split(",")).collect(Collectors.toList()));
        }
        return ResultJson.success(true);
    }

    @ApiOperation("合并支付")
    @PostMapping("order/pay/union")
    @Transactional(rollbackFor = Exception.class)
    public ResultJson<String> payUnion(@Validated @RequestBody  @ApiParam(value = "订单id列表", required = true) @NotEmpty Set<Integer> orderIds) {
        Assert.isTrue(orderIds.size() < 999, "订单数量不能超过999");
        List<SaleOrder> orders = SaleOrder.instancesOf(orderIds);
        Integer customerId = OperatorUtil.getPlatformId();
        Assert.isTrue(orders.stream().allMatch(x->x.getCustomerId().equals(customerId) && x.getState().equals(OrderStateEnum.TO_BE_PAID)), "包含不正确的订单");
        //支付单
        List<SaleOrderPay> orderPays = SaleOrderPay.instancesOfOrders(orderIds);
        Assert.isTrue(orderPays.stream().allMatch(x->x.getState().equals(OrderPayStateEnum.TO_BE_PAID)), "包含不正确的支付状态");
        //校验时间
        Optional<LocalDateTime> deadline = orders.stream().map(SaleOrder::getPayDeadline).min(Comparator.comparing(x->x));
        Assert.isTrue(deadline.isPresent() && LocalDateTime.now().isBefore(deadline.get()), "无效的订单数据");

        Set<Integer> parentPayIds = orderPays.stream().map(SaleOrderPay::getParentId).filter(x ->!x.equals(0)).collect(Collectors.toSet());
        if(parentPayIds.isEmpty()) {
            //目标订单都没有在其它的批次里
            if(orderPays.size() == 1) {
                //单个订单直接返回
                return ResultJson.success(orderPays.get(0).getSn());
            } else {
                //合并
                SaleOrderPay newParentPay = SaleOrderPay.newParentInstance(BigDecimal.valueOf(orders.stream().mapToDouble(x->x.getAmount().doubleValue()).sum()));
                orderPays.forEach(x->x.attachParent(newParentPay.getId()).save());
                return ResultJson.success(newParentPay.getSn());
            }
        }
        else {
            List<SaleOrderPay> parentPays = SaleOrderPay.instancesOf(parentPayIds);
            Assert.isTrue(parentPays.stream().allMatch(x->x.getState().equals(OrderPayStateEnum.TO_BE_PAID)), "包含不正确的支付状态");
            List<SaleOrderPay> childPays = SaleOrderPay.instancesOfParents(parentPayIds);
            Set<Integer> childOrderIds = childPays.stream().map(SaleOrderPay::getSaleOrderId).collect(Collectors.toSet());
            if (parentPays.size() == 1 && childOrderIds.size() == orderIds.size() &&  childOrderIds.containsAll(orderIds)) {
                //只有一个批次并且子项完全相同直接返回
                return ResultJson.success(parentPays.get(0).getSn());
            } else {
                //删除原批次
                parentPays.forEach(SaleOrderPay::delete);
                Set<Integer> orderPayIds = orderPays.stream().map(SaleOrderPay::getId).collect(Collectors.toSet());
                childPays.stream().filter(x -> !orderPayIds.contains(x.getId())).forEach(x -> x.attachParent(0).save());
                //重新合并
                SaleOrderPay newParentPay = SaleOrderPay.newParentInstance(BigDecimal.valueOf(orders.stream().mapToDouble(x -> x.getAmount().doubleValue()).sum()));
                orderPays.forEach(x -> x.attachParent(newParentPay.getId()).save());
                return ResultJson.success(newParentPay.getSn());
            }
        }
    }
}
