package org.geely.infrastructure.db.convert;

import org.geely.common.annotation.DataToDoIgnore;
import org.geely.domain.core.data.*;
import org.geely.infrastructure.db.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ricardo zhou
 */
@Mapper
public interface CoreDomainDataConvert {
    CoreDomainDataConvert INSTANCE = Mappers.getMapper(CoreDomainDataConvert.class);

    /**
     * 转换
     *
     * @param customerData 领域对象转
     * @return 数据库映射对象
     */
    @DataToDoIgnore
    CustomerDO convert(CustomerData customerData);

    /**
     * 转换
     *
     * @param customerDO 数据库映射对象
     * @return 领域对象
     */
    CustomerData convert(CustomerDO customerDO);

    /**
     * 转换
     *
     * @param data 领域对象转
     * @return 数据库映射对象
     */
    @DataToDoIgnore
    ShopDO convert(ShopData data);

    /**
     * 转换
     *
     * @param shopDO 数据库映射对象
     * @return 领域对象
     */
    ShopData convert(ShopDO shopDO);

    /**
     * 转换
     *
     * @param data 领域对象转
     * @return 数据库映射对象
     */
    @DataToDoIgnore
    ProductDO convert(ProductData data);

    /**
     * 转换
     *
     * @param productDO 数据库映射对象
     * @return 领域对象
     */
    ProductData convert(ProductDO productDO);

    /**
     * 转换
     *
     * @param data 领域对象转
     * @return 数据库映射对象
     */
    @DataToDoIgnore
    SkuSpecsDO convert(SkuSpecsData data);

    /**
     * 转换
     *
     * @param targetDO 数据库映射对象
     * @return 领域对象
     */
    SkuSpecsData convert(SkuSpecsDO targetDO);

    /**
     * 转换
     *
     * @param data 领域对象转
     * @return 数据库映射对象
     */
    @DataToDoIgnore
    SkuSpecsTypeDO convert(SkuSpecsTypeData data);

    /**
     * 转换
     *
     * @param targetDO 数据库映射对象
     * @return 领域对象
     */
    SkuSpecsTypeData convert(SkuSpecsTypeDO targetDO);

    /**
     * 转换
     *
     * @param data 领域对象转
     * @return 数据库映射对象
     */
    @DataToDoIgnore
    ProductPropsDO convert(ProductPropsData data);

    /**
     * 转换
     *
     * @param targetDO 数据库映射对象
     * @return 领域对象
     */
    ProductPropsData convert(ProductPropsDO targetDO);

    /**
     * 转换
     *
     * @param data 领域对象转
     * @return 数据库映射对象
     */
    @DataToDoIgnore
    SkuDO convert(SkuData data);

    /**
     * 转换
     *
     * @param targetDO 数据库映射对象
     * @return 领域对象
     */
    SkuData convert(SkuDO targetDO);

    /**
     * 转换
     *
     * @param data 领域对象转
     * @return 数据库映射对象
     */
    @DataToDoIgnore
    SkuImageDO convert(SkuImageData data);

    /**
     * 转换
     *
     * @param targetDO 数据库映射对象
     * @return 领域对象
     */
    SkuImageData convert(SkuImageDO targetDO);

    /**
     * 转换
     *
     * @param data 领域对象转
     * @return 数据库映射对象
     */
    @DataToDoIgnore
    SkuSpecsRelDO convert(SkuSpecsRelData data);

    /**
     * 转换
     *
     * @param targetDO 数据库映射对象
     * @return 领域对象
     */
    SkuSpecsRelData convert(SkuSpecsRelDO targetDO);

    /**
     * 转换
     *
     * @param data 领域对象转
     * @return 数据库映射对象
     */
    @DataToDoIgnore
    SkuPriceDO convert(SkuPriceData data);

    /**
     * 转换
     *
     * @param targetDO 数据库映射对象
     * @return 领域对象
     */
    SkuPriceData convert(SkuPriceDO targetDO);

    /**
     * 转换
     *
     * @param data 领域对象转
     * @return 数据库映射对象
     */
    @DataToDoIgnore
    MaterialDO convert(MaterialData data);

    /**
     * 转换
     *
     * @param targetDO 数据库映射对象
     * @return 领域对象
     */
    MaterialData convert(MaterialDO targetDO);

    /**
     * 转换
     *
     * @param data 领域对象转
     * @return 数据库映射对象
     */
    @DataToDoIgnore
    ProductImageDO convert(ProductImageData data);

    /**
     * 转换
     *
     * @param targetDO 数据库映射对象
     * @return 领域对象
     */
    ProductImageData convert(ProductImageDO targetDO);

    /**
     * 转换
     *
     * @param saleOrderDO 数据库映射对象
     * @return 领域对象
     */
    SaleOrderData convert(SaleOrderDO saleOrderDO);

    /**
     * 转换
     *
     * @param data 领域对象转
     * @return 数据库映射对象
     */
    @DataToDoIgnore
    SaleOrderDO convert(SaleOrderData data);

    /**
     * 转换
     *
     * @param saleOrderItemDO 数据库映射对象
     * @return 领域对象
     */
    SaleOrderItemData convert(SaleOrderItemDO saleOrderItemDO);

    /**
     * 转换
     *
     * @param data 领域对象转
     * @return 数据库映射对象
     */
    @DataToDoIgnore
    SaleOrderItemDO convert(SaleOrderItemData data);

    /**
     * 转换
     *
     * @param customerAddressData 领域对象转
     * @return 数据库映射对象
     */
    @DataToDoIgnore
    CustomerAddressDO convert(CustomerAddressData customerAddressData);

    /**
     * 转换
     *
     * @param customerAddressDO 数据库映射对象
     * @return 领域对象
     */
    CustomerAddressData convert(CustomerAddressDO customerAddressDO);

    HomePageSkuDO convert(HomePageSkuData homePageSkuData);

    HomePageSkuData convert(HomePageSkuDO homePageSkuDO);

    /**
     * 转换
     *
     * @param deliveryNoteDO 数据库映射对象
     * @return 领域对象
     */
    DeliveryNoteData convert(DeliveryNoteDO deliveryNoteDO);

    /**
     * 转换
     *
     * @param data 领域对象转
     * @return 数据库映射对象
     */
    @DataToDoIgnore
    DeliveryNoteDO convert(DeliveryNoteData data);

    /**
     * 转换
     *
     * @param deliveryPackageDO 数据库映射对象
     * @return 领域对象
     */
    DeliveryPackageData convert(DeliveryPackageDO deliveryPackageDO);

    /**
     * 转换
     * @param data 领域对象转
     * @return 数据库映射对象
     */
    @DataToDoIgnore
    DeliveryPackageDO convert(DeliveryPackageData data);
    /**
     * 转换
     * @param data 领域对象转
     * @return 数据库映射对象
     */
    @DataToDoIgnore
    CartDO convert(CartData data);

    /**
     * 转换
     * @param cartDO 数据库映射对象
     * @return 领域对象
     */
    CartData convert(CartDO cartDO);
    /**
     * 转换
     * @param data 领域对象转
     * @return 数据库映射对象
     */
    @DataToDoIgnore
    SaleOrderPayDO convert(SaleOrderPayData data);

    /**
     * 转换
     * @param payDO 数据库映射对象
     * @return 领域对象
     */
    SaleOrderPayData convert(SaleOrderPayDO payDO);
}
