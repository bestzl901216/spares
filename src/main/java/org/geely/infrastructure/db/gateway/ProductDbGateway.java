package org.geely.infrastructure.db.gateway;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.geely.controller.dto.*;
import org.geely.controller.dto.site.*;

import java.util.List;
import java.util.Set;

/**
 * @author ricardo zhou
 */
@Mapper
public interface ProductDbGateway {
    /**
     * sku图片列表
     *
     * @param skuIds sku id
     * @return sku图片列表
     */
    List<SkuImageDTO> skuImageList(@Param("skuIds") List<Integer> skuIds);
    List<ProductTagDTO> productTagList(@Param("spuIds") List<Integer> spuIds);
    /**
     * 分页查询商品
     *
     * @param page     分页参数
     * @param shopId   店铺id
     * @return 消息集合
     */
    IPage<ProductShopListDTO> productShopPage(Page<?> page, @Param("shopId") Integer shopId);

    /**
     * 分页查询商品
     *
     * @param page     分页参数
     * @param supplierId   商家id
     * @return 消息集合
     */
    IPage<ProductSupplierListDTO> productSupplierPage(Page<?> page, @Param("supplierId") Integer supplierId);

    List<SkuBuilderDTO> listSkuBuilder(@Param("productIds") Set<Integer> productIds);

    /**
     * 商城端商品查询
     *
     * @param page 分页参数
     * @param dto  查询条件
     * @return 分页查询结果
     */
    IPage<SkuListDTO> skuSitePage(Page<?> page, @Param("dto") SkuSiteQueryDTO dto);

    /**
     * @param mallId 商城id
     * @param customerId 客户id
     * @param accountId 账号id
     * @return 购物车列表
     */
    List<CartShopSkuDTO> cartList(@Param("mallId") Integer mallId, @Param("customerId") Integer customerId, @Param("accountId") Integer accountId);
    /**
     * @param mallId 商城id
     * @param customerId 客户id
     * @param skuIds sku_ids
     * @return 下单sku信息
     */
    List<OrderSkuDTO> orderSkuList(@Param("mallId") Integer mallId, @Param("customerId") Integer customerId, @Param("skuIds") Set<Integer> skuIds);

    Set<Integer> getBrandIds(@Param("mallId") Integer mallId, @Param("customerId") Integer customerId, @Param("keywords") String keywords);

    Set<Integer> getTagIds(@Param("mallId")Integer mallId, @Param("customerId")Integer customerId, @Param("keywords") String keywords);

    List<Integer> getTopOneFreeShippingSku(@Param("mallId")Integer mallId, @Param("customerId")Integer customerId, @Param("keywords") String keywords);

    Set<Integer> getCategoryIds(@Param("mallId")Integer mallId, @Param("customerId")Integer customerId, @Param("keywords") String keywords);

    /**
     * @param mallId 商城id
     * @param customerId 客户id
     * @param skuIds sku_ids
     * @return 下单sku信息
     */
    List<SkuShopOrderingDTO> orderingSkuList(@Param("mallId") Integer mallId, @Param("customerId") Integer customerId, @Param("skuIds") Set<Integer> skuIds);

}
