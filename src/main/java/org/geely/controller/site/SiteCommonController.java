package org.geely.controller.site;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.geely.common.annotation.NoLogin;
import org.geely.common.constants.MvcConstant;
import org.geely.common.enums.SelectorEnum;
import org.geely.common.model.ResultJson;
import org.geely.common.utils.OperatorUtil;
import org.geely.controller.dto.*;
import org.geely.controller.dto.site.SearchHistoryCreateDTO;
import org.geely.controller.dto.site.SearchHistoryDTO;
import org.geely.domain.common.SearchHistory;
import org.geely.domain.core.ProductBrand;
import org.geely.domain.core.ProductCategory;
import org.geely.domain.core.ProductQuality;
import org.geely.domain.core.ProductTag;
import org.geely.infrastructure.db.convert.SupportDomainDtoConvert;
import org.geely.infrastructure.db.gateway.HomePageCategoryDbGateway;
import org.geely.infrastructure.db.gateway.MallDbGateway;
import org.geely.infrastructure.db.gateway.ProductDbGateway;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author ricardo zhou
 */
@Api(value = "商城端-通用数据接口", tags = "商城端-通用数据接口")
@Slf4j
@RestController
@RequestMapping(MvcConstant.SITE_COMMON)
public class SiteCommonController {
    @Resource
    private MallDbGateway mallDbGateway;
    @Resource
    private HomePageCategoryDbGateway homePageCategoryDbGateway;
    @Resource
    private ProductDbGateway productDbGateway;

    @ApiOperation("商城下拉框")
    @GetMapping("selector/{type}")
    public ResultJson<List<SelectorDTO>> getSelector(@PathVariable SelectorEnum type) {
        List<SelectorDTO> result = new ArrayList<>();
        switch (type) {
            case PRODUCT_BRAND:
                result = ProductBrand.instancesOf(OperatorUtil.getMallId()).stream().map(ProductBrand::getSelector).collect(Collectors.toList());
                break;
            case PRODUCT_QUALITY:
                result = ProductQuality.instancesOf(OperatorUtil.getMallId()).stream().map(ProductQuality::getSelector).collect(Collectors.toList());
                break;
            case PRODUCT_TAG:
                result = ProductTag.instancesOf(OperatorUtil.getMallId()).stream().map(ProductTag::getSelector).collect(Collectors.toList());
                break;
        }
        return ResultJson.success(result);
    }

    @ApiOperation(value = "平台基础信息", notes = "商城名、宣传标语、服务热线、备案信息、轮播图")
    @NoLogin
    @GetMapping("baseInfo")
    public ResultJson<MallBaseInfoDTO> getBaseInfo(@RequestHeader(value = MvcConstant.MALL_ID, required = false) Integer mallId) {
        if (mallId == null) {
            mallId = 0;
        }
        MallBaseInfoDTO baseInfoDto;
        if (mallId == 0)
            baseInfoDto = mallDbGateway.getMallPlatformInfo();
        else
            baseInfoDto = mallDbGateway.getMallBaseInfo(mallId);
        //轮播广告位
        if (baseInfoDto != null && baseInfoDto.getId() > 0) {
            baseInfoDto.setUrls(mallDbGateway.listMallBanner(mallId));
        }
        return ResultJson.success(baseInfoDto);
    }

    @ApiOperation("分类列表")
    @GetMapping("category/tree")
    public ResultJson<List<ProductCategoryDTO>> productCategoryTree() {
        Integer mallId = OperatorUtil.getMallId();
        Integer customerId =  OperatorUtil.getPlatformId();
        Set<Integer> categoryIds = productDbGateway.getCategoryIds(mallId, customerId, "");
        List<ProductCategoryDTO> trees = ProductCategory.listTrees(OperatorUtil.getMallId());
        List<ProductCategoryDTO> results = ProductCategory.filterByIdAndParents(trees, categoryIds);
        return ResultJson.success(results);
    }

    @ApiOperation("分类、sku广告位列表")
    @GetMapping("homepage/category/list")
    public ResultJson<List<HomePageCategoryDTO>> homePageCategoryList() {
        List<HomePageCategoryDTO> result = new ArrayList<>();
        List<HomePageSkuListDTO> skus = homePageCategoryDbGateway.homePageCategoryList(OperatorUtil.getMallId());
        if (CollectionUtils.isEmpty(skus))
            return ResultJson.success(result);

        List<ProductTagDTO> productTags = null;
        List<Integer> spuIds = skus.stream().filter(e -> e.productId != null).map(HomePageSkuListDTO::getProductId).distinct().collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(spuIds))
            productTags = productDbGateway.productTagList(spuIds);
        List<ProductTagDTO> finalProductTags = productTags;

        skus.stream().collect(Collectors.groupingBy(HomePageSkuListDTO::getC1Id)).forEach((k, v) ->
        {
            HomePageCategoryDTO cItem = new HomePageCategoryDTO();
            v.forEach(s -> {
                if (CollectionUtils.isNotEmpty(finalProductTags))
                    s.spuTags = finalProductTags.stream().filter(e -> Objects.equals(e.getProductId(), s.productId)).collect(Collectors.toList());
            });

            HomePageSkuListDTO sItem = v.get(0);
            cItem.c1Id = sItem.c1Id;
            cItem.c1Name = sItem.c1Name;
            cItem.homePageSkus = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(v))
                cItem.homePageSkus = v.stream().filter(e -> e.skuId != null && e.productId != null).map(SupportDomainDtoConvert.INSTANCE::convert).collect(Collectors.toList());
            cItem.imgUrl = sItem.imgUrl;
            cItem.sort = sItem.sort;
            cItem.c1Code = sItem.c1Code;
            result.add(cItem);
        });
        return ResultJson.success(result);
    }

    @ApiOperation("添加搜索记录")
    @PostMapping("searchHistory")
    public ResultJson<Void> searchHistory(@Validated @RequestBody SearchHistoryCreateDTO dto) {
        Integer mallId = OperatorUtil.getMallId();
        Integer customerId = OperatorUtil.getPlatformId();
        Integer accountId = OperatorUtil.getAccountId();
        try {
            SearchHistory.instanceOf(mallId, customerId, accountId, dto.getKeyword()).save();
        } catch (Exception e) {
            SearchHistory.newInstance(mallId, customerId, accountId, dto.getKeyword());
        }
        return ResultJson.success();
    }

    @ApiOperation("最近10条搜索记录")
    @GetMapping("searchHistory")
    public ResultJson<List<SearchHistoryDTO>> searchHistory() {
        Integer mallId = OperatorUtil.getMallId();
        Integer customerId = OperatorUtil.getPlatformId();
        Integer accountId = OperatorUtil.getAccountId();
        List<SearchHistoryDTO> result = mallDbGateway.searchHistoryList(mallId, customerId, accountId);
        return ResultJson.success(result);
    }
}
