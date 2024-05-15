package org.geely.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.geely.common.constants.MvcConstant;
import org.geely.common.model.ResultJson;
import org.geely.common.utils.OperatorUtil;
import org.geely.controller.dto.*;
import org.geely.controller.dto.site.SaleOrderFlowDTO;
import org.geely.domain.common.Account;
import org.geely.domain.common.MallBankAccount;
import org.geely.domain.common.MarketChannel;
import org.geely.domain.common.data.MallBankAccountData;
import org.geely.domain.core.*;
import org.geely.domain.core.data.ProductCategoryData;
import org.geely.domain.core.data.SkuSpecsData;
import org.geely.domain.core.data.SkuSpecsTypeData;
import org.geely.domain.core.data.SupplierData;
import org.geely.infrastructure.db.gateway.MallDbGateway;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Set;


/**
 * @author ricardo zhou
 */
@Api(value = "租户管理平台接口", tags = "租户管理平台接口")
@Slf4j
@RestController
@RequestMapping(MvcConstant.MALL)
public class MallController {
    @Resource
    private MallDbGateway dbGateway;

    @ApiOperation("添加标签")
    @PostMapping("product/tag")
    public ResultJson<Integer> createProductTag(@Validated @RequestBody ProductTagCreateDTO dto) {
        Mall mall = Mall.instanceOf(OperatorUtil.getPlatformId());
        ProductTag tag = mall.createTag(dto.getName(), dto.getRemark(), dto.getFontColor(), dto.getBackgroundColor());
        return ResultJson.success(tag.getId());
    }

    @ApiOperation("删除标签")
    @DeleteMapping("product/tag/{id}")
    public ResultJson<Integer> deleteProductTag(@PathVariable Integer id) {
        ProductTag tag = ProductTag.instanceOf(id, OperatorUtil.getPlatformId());
        tag.delete();
        return ResultJson.success(1);
    }

    @ApiOperation("标签列表")
    @GetMapping("product/tag/page")
    public ResultJson<Page<ProductTagPageDTO>> pageProductTag(@RequestParam Long current, @RequestParam Long size) {
        Page<ProductTagPageDTO> result = ProductTag.page(current, size, OperatorUtil.getPlatformId());
        return ResultJson.success(result);
    }

    @ApiOperation("添加品牌")
    @PostMapping("product/brand")
    public ResultJson<Integer> createProductBrand(@Validated @RequestBody ProductBrandCreateDTO dto) {
        Mall mall = Mall.instanceOf(OperatorUtil.getPlatformId());
        ProductBrand brand = mall.createBrand(dto.getName(), dto.getLogo());
        return ResultJson.success(brand.getId());
    }

    @ApiOperation("删除品牌")
    @DeleteMapping("product/brand/{id}")
    public ResultJson<Integer> deleteProductBrand(@PathVariable Integer id) {
        ProductBrand brand = ProductBrand.instancesOf(id, OperatorUtil.getPlatformId());
        brand.delete();
        return ResultJson.success(1);
    }

    @ApiOperation("品牌列表")
    @GetMapping("product/brand/page")
    public ResultJson<Page<ProductBrandDTO>> pageProductBrand(@RequestParam Long current, @RequestParam Long size) {
        Page<ProductBrandDTO> result = ProductBrand.page(current, size, OperatorUtil.getPlatformId());
        return ResultJson.success(result);
    }

    @ApiOperation("添加品质")
    @PostMapping("product/quality")
    public ResultJson<Integer> createProductQuality(@Validated @RequestBody ProductQualityCreateDTO dto) {
        Mall mall = Mall.instanceOf(OperatorUtil.getPlatformId());
        ProductQuality quality = mall.createQuality(dto.getName(), dto.getRemark());
        return ResultJson.success(quality.getId());
    }

    @ApiOperation("删除品质")
    @DeleteMapping("product/quality/{id}")
    public ResultJson<Integer> deleteProductQuality(@PathVariable Integer id) {
        ProductQuality quality = ProductQuality.instanceOf(id, OperatorUtil.getPlatformId());
        quality.delete();
        return ResultJson.success(1);
    }

    @ApiOperation("品质列表")
    @GetMapping("product/quality/page")
    public ResultJson<Page<ProductQualityDTO>> pageProductQuality(@RequestParam Long current, @RequestParam Long size) {
        Page<ProductQualityDTO> result = ProductQuality.page(current, size, OperatorUtil.getPlatformId());
        return ResultJson.success(result);
    }

    @ApiOperation("添加分类")
    @PostMapping("product/category")
    public ResultJson<Integer> createProductCategory(@Validated @RequestBody ProductCategoryCreateDTO dto) {
        ProductCategoryData data = new ProductCategoryData();
        data.setName(dto.getName());
        data.setParentId(dto.getParentId());
        data.setMallId(OperatorUtil.getPlatformId());
        ProductCategory category = ProductCategory.newInstance(data);
        return ResultJson.success(category.getId());
    }

    @ApiOperation("分类列表")
    @GetMapping("product/category/tree")
    public ResultJson<List<ProductCategoryDTO>> productCategoryTree() {
        List<ProductCategoryDTO> result = ProductCategory.listTrees(OperatorUtil.getPlatformId());
        return ResultJson.success(result);
    }

    @ApiOperation("分类上移")
    @PutMapping("product/category/up/{id}")
    public ResultJson<Boolean> productCategoryMoveUp(@PathVariable Integer id) {
        ProductCategory category = ProductCategory.instanceOf(id, OperatorUtil.getPlatformId());
        return ResultJson.success(category.moveUp());
    }

    @ApiOperation("分类下移")
    @PostMapping("product/category/down/{id}")
    public ResultJson<Boolean> productCategoryMoveDown(@PathVariable Integer id) {
        ProductCategory category = ProductCategory.instanceOf(id, OperatorUtil.getPlatformId());
        return ResultJson.success(category.moveDown());
    }

    @ApiOperation("创建收款账户")
    @PostMapping("bank/account")
    public ResultJson<Integer> createBankAccount(@Validated @RequestBody MallBankAccountCreateDTO dto) {
        MallBankAccountData accountData = new MallBankAccountData(dto);
        accountData.setMallId(OperatorUtil.getPlatformId());
        MallBankAccount instance = MallBankAccount.newInstance(accountData);
        return ResultJson.success(instance.getId());
    }

    @ApiOperation("收款账户列表")
    @GetMapping("bank/account/page")
    public ResultJson<Page<MallBankAccountDTO>> pageBankAccount(@Validated MallBankAccountPageDTO pageDTO) {
        pageDTO.setMallId(OperatorUtil.getPlatformId());
        Page<MallBankAccountDTO> result = MallBankAccount.page(pageDTO);
        return ResultJson.success(result);
    }

    @ApiOperation("创建商家")
    @PostMapping("supplier")
    @Transactional(rollbackFor = Exception.class)
    public ResultJson<Integer> createSupplier(@Validated @RequestBody SupplierCreateDTO dto) {
        SupplierData data = new SupplierData(dto);
        data.setMallId(OperatorUtil.getPlatformId());
        Supplier supplier = Supplier.newInstance(data);
        Set<MarketChannel> marketChannels = MarketChannel.instanceOf(dto.getMarketChannels());
        supplier.associateMarketChannels(marketChannels);
        Account.newInstanceOrGetBy(dto.getAdminPhone()).associate(supplier.createAdminRole());
        return ResultJson.success(supplier.getId());
    }

    @ApiOperation("商家列表")
    @GetMapping("supplier/page")
    public ResultJson<IPage<SupplierDTO>> pageSupplier(@Validated SupplierQueryDTO dto) {
        dto.setMallId(OperatorUtil.getPlatformId());
        Page<SupplierDTO> result = Page.of(dto.getCurrent(), dto.getSize());
        dbGateway.pageSupplier(result, dto.getMallId(), dto.getName(), dto.getMarketChannelId(), dto.getSapId(), dto.getState());
        return ResultJson.success(result);
    }

    @ApiOperation("添加规格类型")
    @PostMapping("sku/specs/type")
    public ResultJson<Integer> createSkuSpecsType(@Validated @RequestBody SkuSpecsTypeCreateDTO dto) {
        SkuSpecsTypeData data = new SkuSpecsTypeData();
        data.setMallId(OperatorUtil.getPlatformId());
        data.setName(dto.getName());
        SkuSpecsType instance = SkuSpecsType.newInstance(data);
        return ResultJson.success(instance.getId());
    }

    @ApiOperation("添加规格")
    @PostMapping("sku/specs")
    public ResultJson<Integer> createSkuSpecs(@Validated @RequestBody SkuSpecsCreateDTO dto) {
        SkuSpecsType specsType = SkuSpecsType.InstanceOf(dto.getTypeId(), OperatorUtil.getPlatformId());
        SkuSpecs instance = specsType.getSpecsByName(dto.getName());
        if (instance.getData() == null) {
            SkuSpecsData newData = new SkuSpecsData();
            newData.setTypeId(dto.getTypeId());
            newData.setName(dto.getName());
            newData.setSelectable(true);
            SkuSpecs newInstance = SkuSpecs.newInstance(newData);
            return ResultJson.success(newInstance.getId());
        } else {
            instance.setSelectable();
            instance.save();
            return ResultJson.success(instance.getId());
        }
    }

    @ApiOperation("销售订单列表")
    @GetMapping("saleOrders")
    public ResultJson<Page<MallSaleOrderDTO>> saleOrderPage(
            @ApiParam(name = "current", required = true, value = "页码")
            @Min(1) @RequestParam Long current,
            @ApiParam(name = "size", required = true, value = "每页数量")
            @Min(1) @Max(1000) @RequestParam Long size) {
        Page<MallSaleOrderDTO> page = new Page<>(current, size);
        dbGateway.saleOrderPage(page, OperatorUtil.getPlatformId());
        return ResultJson.success(page);
    }

    @ApiOperation("订单详情")
    @GetMapping("saleOrders/{id}")
    public ResultJson<MallSaleOrderDetailDTO> saleOrderDetail(@PathVariable Integer id) {
        MallSaleOrderDetailDTO saleOrderDetail = dbGateway.getSaleOrderDetail(id, OperatorUtil.getPlatformId());
        saleOrderDetail = saleOrderDetail.afterPropertiesSet();
        return ResultJson.success(saleOrderDetail);
    }

    @ApiOperation("订单详情-订单子项")
    @GetMapping("saleOrders/{id}/items")
    public ResultJson<List<MallSaleOrderItemDTO>> saleOrderItems(@PathVariable Integer id) {
        List<MallSaleOrderItemDTO> saleOrderItems = dbGateway.getSaleOrderItems(id, OperatorUtil.getPlatformId());
        return ResultJson.success(saleOrderItems);
    }

    @ApiOperation("订单详情-发货单列表")
    @GetMapping("saleOrders/{id}/delivery")
    public ResultJson<List<MallDeliveryOrderDTO>> deliveryOrderList(@PathVariable Integer id) {
        SaleOrder saleOrder = SaleOrder.instanceOf(id);
        Assert.isTrue(saleOrder.getMallId().equals(OperatorUtil.getPlatformId()), "无权限操作");
        return ResultJson.success(dbGateway.deliveryOrderList(id));
    }

    @ApiOperation("订单详情-追踪记录")
    @GetMapping("saleOrders/{id}/flow")
    public ResultJson<List<SaleOrderFlowDTO>> saleOrderFlow(@PathVariable Integer id) {
        SaleOrder saleOrder = SaleOrder.instanceOf(id);
        Assert.isTrue(saleOrder.getMallId().equals(OperatorUtil.getPlatformId()), "无权限操作");
        return ResultJson.success(dbGateway.saleOrderFlow(id));
    }
}
