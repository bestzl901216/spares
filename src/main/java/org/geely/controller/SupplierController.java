package org.geely.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.PhoneUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.geely.common.annotation.OperateLog;
import org.geely.common.constants.MvcConstant;
import org.geely.common.enums.OrderPayApproveStateEnum;
import org.geely.common.enums.OrderPayStateEnum;
import org.geely.common.enums.OrderStateEnum;
import org.geely.common.enums.PlatformTypeEnum;
import org.geely.common.model.ResultJson;
import org.geely.common.utils.OperatorUtil;
import org.geely.controller.dto.*;
import org.geely.controller.dto.site.CustomerDeliveryPackageDTO;
import org.geely.controller.dto.site.SaleOrderFlowDTO;
import org.geely.domain.common.MarketChannelRel;
import org.geely.domain.common.Shop;
import org.geely.domain.core.*;
import org.geely.domain.core.data.SkuPriceData;
import org.geely.domain.support.SaleOrderFlow;
import org.geely.domain.support.data.RoleData;
import org.geely.infrastructure.db.gateway.ProductDbGateway;
import org.geely.infrastructure.db.gateway.SaleOrderDbGateway;
import org.geely.infrastructure.db.gateway.ShopDbGateway;
import org.geely.infrastructure.db.gateway.SupplierDbGateway;
import org.hibernate.validator.constraints.Length;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author ricardo zhou
 */
@Api(value = "商家管理平台接口", tags = "商家管理平台接口")
@Slf4j
@RestController
@RequestMapping(MvcConstant.SUPPLIER)
public class SupplierController {
    @Resource
    private SupplierDbGateway supplierDbGateway;
    @Resource
    private ProductDbGateway productDbGateway;
    @Resource
    private SaleOrderDbGateway saleOrderDbGateway;
    @Resource
    private ShopDbGateway shopDbGateway;

    @ApiOperation("店铺列表")
    @GetMapping("shop")
    public ResultJson<Page<ShopDTO>> shopPage(
            @ApiParam(name = "current", required = true, value = "页码")
            @Min(1)
            @RequestParam Long current,
            @ApiParam(name = "size", required = true, value = "每页数量")
            @Min(1)
            @Max(1000)
            @RequestParam Long size,
            @ApiParam(name = "shopName", value = "店铺名称")
            @RequestParam(required = false) String shopName,
            @ApiParam(name = "channelId", value = "渠道编号")
            @RequestParam(required = false) Integer channelId,
            @ApiParam(name = "state", value = "状态")
            @RequestParam(required = false) Integer state
    ) {
        Page<ShopDTO> page = new Page<>(current, size);
        RoleData roleData = OperatorUtil.getRoleData();
        supplierDbGateway.shopPage(page, roleData.getPlatformId(), shopName, channelId, state);
        return ResultJson.success(page);
    }

    @ApiOperation("店鋪")
    @GetMapping("shop/{shopId}")
    public ResultJson<ShopDTO> getShop(
            @ApiParam(name = "shopId", required = true, value = "店铺编号")
            @PathVariable Integer shopId
    ) {
        Shop shop = Shop.instanceOfId(shopId);
        return ResultJson.success(null);
    }

    @ApiOperation("员工列表")
    @GetMapping("staff")
    public ResultJson<Page<StaffDTO>> getStaffPaging(@ApiParam(value = "手机号")
                                                     @Length(max = 11) @RequestParam(required = false) String phone,
                                                     @ApiParam(name = "current", required = true, value = "页码")
                                                     @Min(1) @RequestParam Long current,
                                                     @ApiParam(name = "size", required = true, value = "每页数量")
                                                     @Min(1) @Max(1000) @RequestParam Long size) {
        Page<StaffDTO> page = new Page<>(current, size);
        RoleData roleData = OperatorUtil.getRoleData();
        supplierDbGateway.staffPage(page, roleData.getPlatformId(), phone);
        //一阶段手机号掩码处理，二阶段删除
        String myPhone = OperatorUtil.getAccountPhone();
        for (StaffDTO record : page.getRecords()) {
            if(myPhone.equals(record.getPhone())) {
                continue;
            }
            record.setPhone(PhoneUtil.hideBetween(record.getPhone()).toString());
        }
        return ResultJson.success(page);
    }

    @ApiOperation("角色列表")
    @GetMapping("role")
    public ResultJson<Page<RoleDTO>> getRolePaging(@ApiParam(name = "current", required = true, value = "页码")
                                                   @Min(1) @RequestParam Long current,
                                                   @ApiParam(name = "size", required = true, value = "每页数量")
                                                   @Min(1) @Max(1000) @RequestParam Long size) {
        Page<RoleDTO> page = new Page<>(current, size);
        supplierDbGateway.rolePage(page, 1);
        return ResultJson.success(page);
    }

    @ApiOperation("物料列表")
    @GetMapping("material")
    public ResultJson<Page<MaterialDTO>> getMaterialPaging(@ApiParam(value = "物料编码/描述/oe号")
                                                           @Length(max = 20)
                                                           @RequestParam(required = false)
                                                           String keyword,
                                                           @ApiParam(value = "后台类目")
                                                           @RequestParam(required = false)
                                                           String category,
                                                           @ApiParam(value = "店铺id")
                                                           @RequestParam(required = false)
                                                           List<Integer> shopIds,
                                                           @ApiParam(name = "current", required = true, value = "页码")
                                                           @Min(1) @RequestParam Long current,
                                                           @ApiParam(name = "size", required = true, value = "每页数量")
                                                           @Min(1) @Max(1000) @RequestParam Long size) {
        Page<MaterialDTO> page = new Page<>(current, size);
        RoleData roleData = OperatorUtil.getRoleData();
        page.setOptimizeCountSql(false);
        supplierDbGateway.materialPage(page, roleData.getPlatformId(), keyword, category, shopIds);
        return ResultJson.success(page);
    }

    @ApiOperation("销售订单列表")
    @GetMapping("saleOrders")
    public ResultJson<Page<SupplierSaleOrderDTO>> saleOrders(@ApiParam(name = "current", required = true, value = "页码")
                                                             @Min(1) @RequestParam Long current,
                                                             @ApiParam(name = "size", required = true, value = "每页数量")
                                                             @Min(1) @Max(1000) @RequestParam Long size) {
        Page<SupplierSaleOrderDTO> page = new Page<>(current, size);
        Integer supplierId = OperatorUtil.getPlatformId();
        supplierDbGateway.saleOrders(page, supplierId);
        return ResultJson.success(page);
    }

    @ApiOperation("销售订单-支付明细")
    @GetMapping("payApprove/{sn}")
    public ResultJson<SupplierSaleOrderPayApproveDTO> payApprove(@PathVariable String sn) {
        Integer supplierId = OperatorUtil.getPlatformId();
        return ResultJson.success(supplierDbGateway.payApprove(sn, supplierId));
    }

    @ApiOperation("销售订单-收款审核")
    @PostMapping("order/pay/approve")
    @Transactional(rollbackFor = Exception.class)
    public ResultJson<Boolean> orderPayApprove(@Validated @RequestBody SupplierPayApproveDTO param) {
        if (!param.isSuccess()) {
            Assert.isFalse(StringUtils.isNullOrEmpty(param.getRemark()), "失败原因不能为空");
        }
        Integer supplierId = OperatorUtil.getPlatformId();
        //支付单
        SaleOrderPay orderPay = SaleOrderPay.instanceOf(param.getSn());
        Assert.isTrue(orderPay.getState().equals(OrderPayStateEnum.PENDING_APPROVAL), "不正确的支付状态");
        //订单
        List<SaleOrder> orders = SaleOrder.instancesOf(orderPay.getSaleOrderIds()).stream().filter(x -> x.getSupplerId().equals(supplierId)).collect(Collectors.toList());
        Assert.isTrue(orders.stream().allMatch(x -> x.getState().equals(OrderStateEnum.TO_BE_APPROVED)), "不正确的订单状态");
        Set<Integer> orderIds = orders.stream().map(SaleOrder::getId).collect(Collectors.toSet());
        //支付凭证
        SaleOrderPayApprove thisApprove = SaleOrderPayApprove.instancesOf(orderPay.getId()).stream().filter(x -> x.getSupplierId().equals(supplierId) && x.getState() == OrderPayApproveStateEnum.PENDING_APPROVAL).findFirst().orElse(null);
        Assert.notNull(thisApprove, "找不到支付凭证");
        assert thisApprove != null;
        if (param.isSuccess()) {
            //更新凭证状态
            thisApprove.success(param.getRemark()).save();
            //更新订单状态
            for (SaleOrder order : orders) {
                order.approved(orderPay.getPayTime()).save();
                SaleOrderFlow.newConfirmOfflinePayRecord(order.getId(), orderPay.getSn());
            }
            //更新支付单状态
            orderPay.approved(orderIds).save();
        } else {
            //更新凭证状态
            thisApprove.fail(param.getRemark()).save();
            //更新订单状态
            Supplier supplier = Supplier.instanceOf(supplierId);
            Mall mall = Mall.instanceOf(supplier.getMallId());
            for (SaleOrder order : orders) {
                order.unApproved(mall.getOrderPayExpiration()).save();
                SaleOrderFlow.newOfflinePayFailRecord(order.getId(), orderPay.getSn(), param.getRemark());
            }
            //更新支付单状态
            orderPay.unApproved(orderIds).save();
            //保存订单批次记录
            if (orderPay.isBatchPay()) {
                BatchPayFailRel.newInstances(orderPay.getId(), orderPay.getSaleOrderIds());
            }
        }
        return ResultJson.success(true);
    }

    @ApiOperation("销售订单详情")
    @GetMapping("saleOrders/{sn}")
    @OperateLog(module = "商家后台-订单管理", operateName = "查看订单详情", type = "其他")
    public ResultJson<SupplierSaleOrderDetailDTO> saleOrder(@PathVariable String sn) {
        Integer supplierId = OperatorUtil.getPlatformId();
        SupplierSaleOrderDetailDTO detail = supplierDbGateway.saleOrder(sn, supplierId);
        Assert.notNull(detail, "未知的订单详情");
        return ResultJson.success(detail.afterPropertiesSet());
    }

    @ApiOperation("商品详情")
    @GetMapping("product/{id}")
    public ResultJson<ProductSupplierDTO> productDetail(@PathVariable Integer id) {
        Product product = Product.instanceOfSupplier(id, OperatorUtil.getPlatformId());
        return ResultJson.success(product.getSupplierDTO());
    }

    @ApiOperation("商品列表")
    @GetMapping("product/page")
    public ResultJson<Page<ProductSupplierListDTO>> productPage(@Validated @ApiParam(name = "current", required = true, value = "页码")
                                                                @NotNull @Min(1) @RequestParam Long current,
                                                                @ApiParam(name = "size", required = true, value = "每页数量")
                                                                @NotNull @Min(1) @Max(1000) @RequestParam Long size) {
        Page<ProductSupplierListDTO> page = new Page<>(current, size);
        Integer supplierId = OperatorUtil.getPlatformId();
        //分页主数据
        productDbGateway.productSupplierPage(page, supplierId);
        if (page.getTotal() == 0) {
            return ResultJson.success(page);
        }
        Set<Integer> productIds = page.getRecords().stream().map(ProductSupplierListDTO::getId).collect(Collectors.toSet());
        Set<Integer> shopIds = page.getRecords().stream().map(ProductSupplierListDTO::getShopId).collect(Collectors.toSet());
        //渠道
        Map<Integer, List<MarketChannelRel>> channelMap = MarketChannelRel.instancesOf(PlatformTypeEnum.SHOP, shopIds);
        //SKU数据
        List<SkuBuilderDTO> skus = productDbGateway.listSkuBuilder(productIds);
        Map<Integer, List<SkuBuilderDTO>> skuMap = skus.stream().collect(Collectors.groupingBy(SkuBuilderDTO::getProductId));
        Set<Integer> skuIds = skus.stream().map(SkuBuilderDTO::getId).collect(Collectors.toSet());
        Map<Integer, List<SkuPriceData>> skuPriceMap = SkuPrice.instancesOf(skuIds).stream().map(SkuPrice::getData).collect(Collectors.groupingBy(SkuPriceData::getSkuId));
        //SKU相关数据赋值
        for (ProductSupplierListDTO record : page.getRecords()) {
            List<SkuBuilderDTO> skuList = skuMap.getOrDefault(record.getId(), new ArrayList<>());
            List<MarketChannelRel> channels = channelMap.getOrDefault(record.getShopId(), new ArrayList<>());
            record.buildSkuInfo(skuList, skuPriceMap, channels);
        }
        return ResultJson.success(page);
    }

    @ApiOperation("订单详情-订单子项")
    @GetMapping("saleOrder/{saleOrderId}/items")
    public ResultJson<List<SaleOrderItemDTO>> saleOrderItems(@PathVariable Integer saleOrderId) {
        return ResultJson.success(supplierDbGateway.getSaleOrderItems(saleOrderId, OperatorUtil.getPlatformId()));
    }

    @ApiOperation("订单详情-追踪记录")
    @GetMapping("saleOrder/{id}/flow")
    public ResultJson<List<SaleOrderFlowDTO>> saleOrderFlow(@PathVariable Integer id) {
        SaleOrder saleOrder = SaleOrder.instanceOf(id);
        Assert.isTrue(saleOrder.getSupplerId().equals(OperatorUtil.getPlatformId()), "无权限操作");
        return ResultJson.success(saleOrderDbGateway.saleOrderFlow(id));
    }

    @ApiOperation("发货单列表")
    @GetMapping("saleOrder/{saleOrderId}/delivery")
    public ResultJson<List<DeliveryOrderDTO>> deliveryOrderList(@PathVariable Integer saleOrderId) {
        SaleOrder saleOrder = SaleOrder.instanceOf(saleOrderId);
        Assert.isTrue(saleOrder.getSupplerId().equals(OperatorUtil.getPlatformId()), "无权限操作");
        return ResultJson.success(shopDbGateway.deliveryOrderList(saleOrderId));
    }

    @ApiOperation("订单详情-包裹清单")
    @GetMapping("saleOrders/{id}/delivery/{deliveryId}/packages")
    public ResultJson<List<CustomerDeliveryPackageDTO>> deliveryPackageList(@PathVariable Integer id, @PathVariable Integer deliveryId) {
        SaleOrder saleOrder = SaleOrder.instanceOf(id);
        Assert.isTrue(saleOrder.getSupplerId().equals(OperatorUtil.getPlatformId()), "无权限操作");
        return ResultJson.success(saleOrderDbGateway.deliveryPackageList(id, deliveryId));
    }
}
