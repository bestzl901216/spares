package org.geely.controller.site;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.geely.common.constants.MvcConstant;
import org.geely.common.enums.ProductSelectorEnum;
import org.geely.common.model.ResultJson;
import org.geely.common.utils.OperatorUtil;
import org.geely.controller.dto.*;
import org.geely.controller.dto.site.*;
import org.geely.domain.core.Product;
import org.geely.domain.core.ProductBrand;
import org.geely.domain.core.ProductCategory;
import org.geely.domain.core.ProductTag;
import org.geely.domain.core.data.ProductBrandData;
import org.geely.domain.core.data.ProductTagData;
import org.geely.infrastructure.db.convert.SiteDtoConvert;
import org.geely.infrastructure.db.gateway.ProductDbGateway;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author cong huang
 */
@Api(value = "商城端-商品接口", tags = "商城端-商品接口")
@Slf4j
@RestController
@RequestMapping(MvcConstant.SITE_PRODUCT)
public class SiteProductController {
    @Resource
    private ProductDbGateway productDbGateway;
    @ApiOperation("商品详情")
    @GetMapping("sku/{id}")
    public ResultJson<ProductSiteDTO> productDetail(@PathVariable Integer id) {
        try {
            Product product = Product.instanceOfCustomer(id, OperatorUtil.getPlatformId());
            return ResultJson.success(product.getSiteDTO());
        }
        catch (Exception ex) {
            if(ex.getMessage().equals(Product.INVALID_MSG)) {
                return ResultJson.success(ProductSiteDTO.invalidInstance());
            }
            throw ex;
        }
    }

    @ApiOperation("商品搜索")
    @GetMapping("sku/page")
    public ResultJson<Page<SkuListDTO>> pageSku(@Validated SkuSiteQueryDTO dto) {
        Page<SkuListDTO> page = new Page<>(dto.getCurrent(), dto.getSize());
        page.setOptimizeCountSql(false);
        dto.setCustomerId(OperatorUtil.getPlatformId());
        dto.setMallId(OperatorUtil.getMallId());
        if(dto.getCategoryId()!= null && !dto.getCategoryId().equals(0)) {
            dto.setCategoryIds(ProductCategory.getTreeIds(dto.getMallId(), dto.getCategoryId()));
        }
        if(!StringUtil.isNullOrEmpty(dto.getTagIds())) {
            dto.setTagIdSet(Arrays.stream(dto.getTagIds().split(",")).map(Integer::parseInt).collect(Collectors.toSet()));
        }
        productDbGateway.skuSitePage(page, dto);
        return ResultJson.success(page);
    }

    @ApiOperation("商品下拉框")
    @GetMapping("selector/{type}")
    public ResultJson<List<SelectorDTO>> getSelector(@PathVariable ProductSelectorEnum type, String keywords) {
        List<SelectorDTO> result = new ArrayList<>();
        Integer mallId = OperatorUtil.getMallId();
        Integer customerId =  OperatorUtil.getPlatformId();
        switch (type) {
            case BRAND:
                Set<Integer> brandIds = productDbGateway.getBrandIds(mallId, customerId, keywords);
                for (ProductBrand brand : ProductBrand.instancesOf(brandIds)) {
                    ProductBrandData brandData = brand.getData();
                    result.add(new SelectorImageDTO(brandData.getName(), brandData.getId().toString(), brandData.getLogo()));
                }
                break;
            case TAG:
                Set<Integer> tagIds = productDbGateway.getTagIds(mallId, customerId, keywords);
                for (ProductTag tag : ProductTag.instancesOf(tagIds)) {
                    ProductTagData tagData = tag.getData();
                    result.add(new SelectorTagDTO(tagData.getName(),tagData.getId().toString(),tagData.getFontColor(),tagData.getBackgroundColor()));
                }
                break;
        }
        return ResultJson.success(result);
    }

    @ApiOperation("是否存在包邮商品")
    @GetMapping("free_shipping/exist")
    public ResultJson<Boolean> getSelector(String keywords)
    {
        Integer mallId = OperatorUtil.getMallId();
        Integer customerId =  OperatorUtil.getPlatformId();
        List<Integer> skuIds = productDbGateway.getTopOneFreeShippingSku(mallId, customerId, keywords);
        return ResultJson.success(!skuIds.isEmpty());
    }

    @ApiOperation("商品分类列表")
    @GetMapping("category/tree")
    public ResultJson<List<ProductCategoryDTO>> categoryTree(String keywords) {
        Integer mallId = OperatorUtil.getMallId();
        Integer customerId =  OperatorUtil.getPlatformId();
        Set<Integer> categoryIds = productDbGateway.getCategoryIds(mallId, customerId, keywords);
        List<ProductCategoryDTO> trees = ProductCategory.listTrees(OperatorUtil.getMallId());
        List<ProductCategoryDTO> results = ProductCategory.filterByIdAndParents(trees, categoryIds);
        return ResultJson.success(results);
    }

    @ApiOperation("结算页SKU列表")
    @PostMapping("ordering")
    public ResultJson<List<OrderingListDTO>> orderingSkuList(@Validated @RequestBody @ApiParam(required = true, value = "sku_id列表") @NotEmpty Set<Integer> skuIds) {
        Assert.isFalse(skuIds.size() > 999, "请求项太多");
        //SKU主数据
        List<SkuShopOrderingDTO> fullInfoList = productDbGateway.orderingSkuList(OperatorUtil.getMallId(), OperatorUtil.getPlatformId(), skuIds);
        if (fullInfoList.isEmpty()) {
            return ResultJson.success(new ArrayList<>());
        }
        Assert.isTrue(skuIds.size() == fullInfoList.size(), "包含不正确的skuId");
        List<OrderingListDTO> result = new ArrayList<>();
        Map<Integer, List<SkuShopOrderingDTO>> shopMap = fullInfoList.stream().collect(Collectors.groupingBy(SkuShopOrderingDTO::getShopId));
        //按店铺分组显示
        for (Integer shopId : shopMap.keySet()) {
            OrderingListDTO dto = new OrderingListDTO();
            SkuShopOrderingDTO shop = shopMap.get(shopId).get(0);
            dto.setShopId(shopId);
            dto.setShopName(shop.getShopName());
            dto.setShopState(shop.getShopState().getId());
            List<SkuOrderingDTO> skuDTOList = shopMap.get(shopId).stream().map(SiteDtoConvert.INSTANCE::convert).collect(Collectors.toList());
            dto.setSkus(skuDTOList);
            result.add(dto);
        }
        return ResultJson.success(result);
    }
}
