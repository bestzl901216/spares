package org.geely.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.PhoneUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.geely.common.annotation.OperateLog;
import org.geely.common.constants.MvcConstant;
import org.geely.common.enums.ExpressCompanyEnum;
import org.geely.common.enums.PlatformTypeEnum;
import org.geely.common.enums.ProductStateEnum;
import org.geely.common.enums.SelectorEnum;
import org.geely.common.model.ResultJson;
import org.geely.common.utils.OperatorUtil;
import org.geely.controller.dto.*;
import org.geely.controller.dto.site.CustomerDeliveryPackageDTO;
import org.geely.controller.dto.site.SaleOrderFlowDTO;
import org.geely.domain.common.MarketChannelRel;
import org.geely.domain.common.Shop;
import org.geely.domain.common.data.TrackData;
import org.geely.domain.core.*;
import org.geely.domain.core.data.SkuPriceData;
import org.geely.domain.core.data.SkuSpecsData;
import org.geely.domain.support.SaleOrderFlow;
import org.geely.infrastructure.db.gateway.ProductDbGateway;
import org.geely.infrastructure.db.gateway.SaleOrderDbGateway;
import org.geely.infrastructure.db.gateway.ShopDbGateway;
import org.geely.infrastructure.express.ExpressGateway;
import org.hibernate.validator.constraints.Length;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author ricardo zhou
 */
@Api(value = "店铺管理平台接口", tags = "店铺管理平台接口")
@Slf4j
@RestController
@RequestMapping(MvcConstant.SHOP)
public class ShopController {
    @Resource
    private ShopDbGateway shopDbGateway;
    @Resource
    private ProductDbGateway productDbGateway;
    @Resource
    private ExpressGateway expressGateway;
    @Resource
    private SaleOrderDbGateway saleOrderDbGateway;

    @ApiOperation("下拉框")
    @GetMapping("selector/{type}")
    public ResultJson<List<SelectorDTO>> getSelector(@PathVariable SelectorEnum type, @ApiParam(name = "parentValue", value = "上级值") String parentValue) {
        List<SelectorDTO> result = new ArrayList<>();
        Integer shopId = OperatorUtil.getPlatformId();
        Integer mallId = Shop.instanceOfId(shopId).getMallId();
        switch (type) {
            case PRODUCT_BRAND:
                result = ProductBrand.instancesOf(mallId).stream().map(ProductBrand::getSelector).collect(Collectors.toList());
                break;
            case PRODUCT_QUALITY:
                result = ProductQuality.instancesOf(mallId).stream().map(ProductQuality::getSelector).collect(Collectors.toList());
                break;
            case PRODUCT_TAG:
                result = ProductTag.instancesOf(mallId).stream().map(ProductTag::getSelector).collect(Collectors.toList());
                break;
            case SKU_SPECS_TYPE:
                result = SkuSpecsType.instancesOf(mallId).stream().map(SkuSpecsType::getSelector).collect(Collectors.toList());
                break;
            case SKU_SPECS:
                result = SkuSpecs.instancesOf(Integer.parseInt(parentValue), mallId).stream().map(SkuSpecs::getSelector).collect(Collectors.toList());
                break;
            case EXPRESS_COMPANY:
                result = Arrays.stream(ExpressCompanyEnum.values()).map(x -> new SelectorDTO(x.getName(), x.name())).collect(Collectors.toList());
                break;
        }
        return ResultJson.success(result);
    }

    @ApiOperation("添加商品")
    @PostMapping("product")
    @Transactional(rollbackFor = Exception.class)
    public ResultJson<ProductShopDTO> createProduct(@Valid @RequestBody ProductCreateDTO dto) {
        Shop shop = Shop.instanceOfId(OperatorUtil.getPlatformId());
        dto.setMallId(shop.getMallId());
        dto.setSupplierId(shop.getSupplierId());
        dto.setShopId(shop.getShopId());
        Product product = Product.newInstance(dto);
        return ResultJson.success(product.getShopDTO());
    }

    @ApiOperation("修改商品")
    @PutMapping("product")
    @Transactional(rollbackFor = Exception.class)
    public ResultJson<ProductShopDTO> updateProduct(@Valid @RequestBody ProductUpdateDTO dto) {
        Product product = Product.instanceOfShop(dto.getId(), OperatorUtil.getPlatformId());
        product.update(dto);
        return ResultJson.success(product.getShopDTO());
    }

    @ApiOperation("商品详情")
    @GetMapping("product/{id}")
    public ResultJson<ProductShopDTO> productDetail(@PathVariable Integer id) {
        Product product = Product.instanceOfShop(id, OperatorUtil.getPlatformId());
        return ResultJson.success(product.getShopDTO());
    }

    @ApiOperation("商品上架")
    @PutMapping("product/on")
    public ResultJson<Boolean> productsOn(@Validated @RequestBody @ApiParam(required = true) @NotEmpty Set<Integer> ids) {
        List<Product> products = Product.instancesOf(ids, OperatorUtil.getPlatformId());
        for (Product product : products) {
            if (product.getState() == ProductStateEnum.OFF) {
                product.setState(ProductStateEnum.ON).save();
            }
        }
        return ResultJson.success(true);
    }

    @ApiOperation("商品下架")
    @PutMapping("product/off")
    public ResultJson<Boolean> productsOff(@Validated @RequestBody @ApiParam(required = true) @NotEmpty Set<Integer> ids) {
        List<Product> products = Product.instancesOf(ids, OperatorUtil.getPlatformId());
        for (Product product : products) {
            if (product.getState() == ProductStateEnum.ON) {
                product.setState(ProductStateEnum.OFF).save();
            }
        }
        return ResultJson.success(true);
    }

    @ApiOperation("商品列表")
    @GetMapping("product/page")
    public ResultJson<Page<ProductShopListDTO>> productPage(@Validated @ApiParam(name = "current", required = true, value = "页码")
                                                              @NotNull @Min(1) @RequestParam Long current,
                                                                  @ApiParam(name = "size", required = true, value = "每页数量")
                                                              @NotNull @Min(1) @Max(1000) @RequestParam Long size) {
        Page<ProductShopListDTO> page = new Page<>(current, size);
        Integer shopId = OperatorUtil.getPlatformId();
        //分页主数据
        productDbGateway.productShopPage(page, shopId);
        if (page.getTotal() == 0) {
            return ResultJson.success(page);
        }
        Set<Integer> productIds = page.getRecords().stream().map(ProductShopListDTO::getId).collect(Collectors.toSet());
        List<MarketChannelRel> channels = MarketChannelRel.instanceOf(PlatformTypeEnum.SHOP, shopId);
        //SKU信息
        List<SkuBuilderDTO> skus = productDbGateway.listSkuBuilder(productIds);
        Map<Integer, List<SkuBuilderDTO>> skuMap = skus.stream().collect(Collectors.groupingBy(SkuBuilderDTO::getProductId));
        Set<Integer> skuIds = skus.stream().map(SkuBuilderDTO::getId).collect(Collectors.toSet());
        Map<Integer, List<SkuPriceData>> skuPriceMap = SkuPrice.instancesOf(skuIds).stream().map(SkuPrice::getData).collect(Collectors.groupingBy(SkuPriceData::getSkuId));
        //SKU信息赋值
        for (ProductShopListDTO record : page.getRecords()) {
            List<SkuBuilderDTO> skuList = skuMap.getOrDefault(record.getId(), new ArrayList<>());
            record.buildSkuInfo(skuList, skuPriceMap, channels);
        }
        return ResultJson.success(page);
    }

    @ApiOperation("添加或获取sku规格")
    @PostMapping("sku/specs")
    public ResultJson<Integer> getOrCreateSkuSpecs(@Validated @RequestBody SkuSpecsCreateDTO dto) {
        Shop shop = Shop.instanceOfId(OperatorUtil.getPlatformId());
        SkuSpecsType specsType = SkuSpecsType.InstanceOf(dto.getTypeId(), shop.getMallId());
        SkuSpecs specs = specsType.getSpecsByName(dto.getName());
        SkuSpecsData specsData = specs.getData();
        if (specsData != null) {
            return ResultJson.success(specsData.getId());
        }
        SkuSpecsData newData = new SkuSpecsData();
        newData.setName(dto.getName());
        newData.setTypeId(dto.getTypeId());
        SkuSpecs skuSpecs = SkuSpecs.newInstance(newData);
        return ResultJson.success(skuSpecs.getId());
    }

    @ApiOperation("渠道列表")
    @GetMapping("channel/list")
    public ResultJson<List<MarketChannelRelDTO>> channelList() {
        List<MarketChannelRel> channels = MarketChannelRel.instancesOfShop(OperatorUtil.getPlatformId());
        return ResultJson.success(channels.stream().map(MarketChannelRel::getDto).collect(Collectors.toList()));
    }

    @ApiOperation("物料列表")
    @GetMapping("material/page")
    public ResultJson<Page<MaterialDTO>> getMaterialPaging(@ApiParam(value = "物料编码/描述/oe号")
                                                           @Length(max = 20)
                                                           @RequestParam(required = false)
                                                           String keyword,
                                                           @ApiParam(value = "后台类目")
                                                           @RequestParam(required = false)
                                                           String category,
                                                           @ApiParam(name = "current", required = true, value = "页码")
                                                           @Min(1) @RequestParam Long current,
                                                           @ApiParam(name = "size", required = true, value = "每页数量")
                                                           @Min(1) @Max(1000) @RequestParam Long size) {
        Page<MaterialDTO> page = new Page<>(current, size);
        shopDbGateway.materialPage(page, OperatorUtil.getPlatformId(), keyword, category);
        return ResultJson.success(page);
    }

    @ApiOperation("员工列表")
    @GetMapping("staff/page")
    public ResultJson<Page<StaffDTO>> getStaffPaging(@ApiParam(value = "手机号")
                                                     @Length(max = 11) @RequestParam(required = false) String phone,
                                                     @ApiParam(name = "current", required = true, value = "页码")
                                                     @Min(1) @RequestParam Long current,
                                                     @ApiParam(name = "size", required = true, value = "每页数量")
                                                     @Min(1) @Max(1000) @RequestParam Long size) {
        Page<StaffDTO> page = new Page<>(current, size);
        shopDbGateway.staffPage(page, OperatorUtil.getPlatformId(), phone);
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
    @GetMapping("role/page")
    public ResultJson<Page<RoleDTO>> getRolePaging(@ApiParam(name = "current", required = true, value = "页码")
                                                   @Min(1) @RequestParam Long current,
                                                   @ApiParam(name = "size", required = true, value = "每页数量")
                                                   @Min(1) @Max(1000) @RequestParam Long size) {
        Page<RoleDTO> page = new Page<>(current, size);
        shopDbGateway.rolePage(page, OperatorUtil.getPlatformId());
        return ResultJson.success(page);
    }

    @ApiOperation("订单列表")
    @GetMapping("saleOrder")
    public ResultJson<Page<SaleOrderDTO>> saleOrderPage(SaleOrderQueryDTO dto) {
        dto.setShopId(OperatorUtil.getPlatformId());
        Page<SaleOrderDTO> page = new Page<>(dto.getCurrent(), dto.getSize());
        shopDbGateway.saleOrderPage(page, dto);
        return ResultJson.success(page);
    }

    @ApiOperation("快递详情")
    @GetMapping("express")
    public ResultJson<TrackData> expressDetail(@ApiParam(value = "快递公司编码") @RequestParam String company,
                                               @ApiParam(value = "快递单号") @RequestParam String shipSn,
                                               @ApiParam(value = "收、寄件人的电话号码") @RequestParam(required = false) String phone) {
        return ResultJson.success(expressGateway.queryTrack(company, shipSn, phone));
    }

    @ApiOperation("订单详情")
    @GetMapping("saleOrder/{saleOrderId}")
    @OperateLog(module = "店铺后台-订单管理", operateName = "查看订单详情", type = "其他")
    public ResultJson<SaleOrderDetailDTO> saleOrderDetail(@PathVariable Integer saleOrderId) {
        SaleOrderDetailDTO detail = shopDbGateway.saleOrderDetail(saleOrderId, OperatorUtil.getPlatformId());
        Assert.notNull(detail, "未知的订单详情");
        return ResultJson.success(detail.afterPropertiesSet());
    }

    @ApiOperation("订单详情-订单子项")
    @GetMapping("saleOrder/{saleOrderId}/items")
    public ResultJson<List<SaleOrderItemDTO>> saleOrderItems(@PathVariable Integer saleOrderId) {
        return ResultJson.success(shopDbGateway.saleOrderItems(saleOrderId, OperatorUtil.getPlatformId()));
    }

    @ApiOperation("订单详情-追踪记录")
    @GetMapping("saleOrder/{id}/flow")
    public ResultJson<List<SaleOrderFlowDTO>> saleOrderFlow(@PathVariable Integer id) {
        SaleOrder saleOrder = SaleOrder.instanceOf(id);
        Assert.isTrue(saleOrder.getShopId().equals(OperatorUtil.getPlatformId()), "无权限操作");
        return ResultJson.success(shopDbGateway.saleOrderFlow(id));
    }

    @ApiOperation("取消订单")
    @PutMapping("saleOrder/{saleOrderId}/cancel")
    @Transactional(rollbackFor = Exception.class)
    public ResultJson<Void> cancelSaleOrder(@PathVariable Integer saleOrderId, @Validated @RequestParam @Length(max = 100) String reason) {
        SaleOrder saleOrder = SaleOrder.instanceOf(saleOrderId);
        Assert.isTrue(saleOrder.getShopId().equals(OperatorUtil.getPlatformId()), "无权限操作");
        saleOrder.cancel(reason).save();
        SaleOrderFlow.newSellerCancelRecord(saleOrderId);
        //释放库存
        Sku.releaseStock(saleOrder.getSkuQtyMap());
        return ResultJson.success();
    }

    @ApiOperation("发货")
    @PutMapping("saleOrder/{saleOrderId}/delivery")
    @Transactional(rollbackFor = Exception.class)
    public ResultJson<Void> deliverySaleOrder(@PathVariable Integer saleOrderId,@Validated @RequestBody DeliveryCreateDTO dto) {
        SaleOrder saleOrder = SaleOrder.instanceOf(saleOrderId);
        Assert.isTrue(saleOrder.getShopId().equals(OperatorUtil.getPlatformId()), "无权限操作");
        dto.getQuantityList().forEach(e -> saleOrder.updateDeliveryQuantity(e.saleOrderItemId, e.quantity));
        saleOrder.save();
        Map<Integer, Integer> quantityMap = dto.quantityList.stream().collect(Collectors.toMap(e -> e.saleOrderItemId, e -> e.quantity));
        Integer orderReceiveExpiration = Mall.instanceOf(saleOrder.getMallId()).getOrderReceiveExpiration();
        DeliveryOrder deliveryOrder = DeliveryOrder.newInstance(saleOrderId, dto.expressCompany, dto.expressSn, saleOrder.getReceiverPhone(), orderReceiveExpiration, quantityMap);
        SaleOrderFlow.newDeliveryRecord(saleOrderId, deliveryOrder.getDeliverySn());
        if (saleOrder.isAllDelivery()) {
            SaleOrderFlow.newAllDeliveryRecord(saleOrderId);
        }
        return ResultJson.success();
    }

    @ApiOperation("订单详情-包裹清单")
    @GetMapping("saleOrders/{id}/delivery/{deliveryId}/packages")
    public ResultJson<List<CustomerDeliveryPackageDTO>> deliveryPackageList(@PathVariable Integer id, @PathVariable Integer deliveryId) {
        SaleOrder saleOrder = SaleOrder.instanceOf(id);
        Assert.isTrue(saleOrder.getShopId().equals(OperatorUtil.getPlatformId()), "无权限操作");
        return ResultJson.success(saleOrderDbGateway.deliveryPackageList(id, deliveryId));
    }

    @ApiOperation("发货单列表")
    @GetMapping("saleOrder/{saleOrderId}/delivery")
    public ResultJson<List<DeliveryOrderDTO>> deliveryOrderList(@PathVariable Integer saleOrderId) {
        SaleOrder saleOrder = SaleOrder.instanceOf(saleOrderId);
        Assert.isTrue(saleOrder.getShopId().equals(OperatorUtil.getPlatformId()), "无权限操作");
        return ResultJson.success(shopDbGateway.deliveryOrderList(saleOrderId));
    }

    @ApiOperation("分类列表")
    @GetMapping("product/category/tree")
    public ResultJson<List<ProductCategoryDTO>> productCategoryTree() {
        Shop shop = Shop.instanceOfId(OperatorUtil.getPlatformId());
        List<ProductCategoryDTO> result = ProductCategory.listTrees(shop.getMallId());
        return ResultJson.success(result);
    }
}
