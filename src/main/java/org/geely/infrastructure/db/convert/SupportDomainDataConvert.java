package org.geely.infrastructure.db.convert;

import org.geely.common.annotation.DataToDoIgnore;
import org.geely.domain.common.data.AccountData;
import org.geely.domain.common.data.MallBankAccountData;
import org.geely.domain.common.data.MallSapData;
import org.geely.domain.core.data.*;
import org.geely.domain.support.data.AccountRoleRelData;
import org.geely.domain.support.data.RoleData;
import org.geely.domain.support.data.SaleOrderFlowData;
import org.geely.infrastructure.db.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author ricardo zhou
 */
@Mapper
public interface SupportDomainDataConvert {
    SupportDomainDataConvert INSTANCE = Mappers.getMapper(SupportDomainDataConvert.class);

    /**
     * 转换
     *
     * @param accountData 领域对象转
     * @return 数据库映射对象
     */
    @DataToDoIgnore
    AccountDO convert(AccountData accountData);

    /**
     * 转换
     *
     * @param accountDO 数据库映射对象
     * @return 领域对象
     */
    @Mapping(target = "password", ignore = true)
    AccountData convert(AccountDO accountDO);

    /**
     * 转换
     *
     * @param roleData 领域对象转
     * @return 数据库映射对象
     */
    @DataToDoIgnore
    RoleDO convert(RoleData roleData);

    /**
     * 转换
     *
     * @param roleDO 数据库映射对象
     * @return 领域对象
     */
    RoleData convert(RoleDO roleDO);

    /**
     * 转换
     *
     * @param accountRoleRelData 领域对象转
     * @return 数据库映射对象
     */
    @DataToDoIgnore
    AccountRoleRelDO convert(AccountRoleRelData accountRoleRelData);

    /**
     * 转换
     *
     * @param accountRoleRelDO 数据库映射对象
     * @return 领域对象
     */
    AccountRoleRelData convert(AccountRoleRelDO accountRoleRelDO);

    /**
     * 转换
     *
     * @param mallData 领域对象转
     * @return 数据库映射对象
     */
    @DataToDoIgnore
    MallDO convert(MallData mallData);

    /**
     * 转换
     *
     * @param mallDO 数据库映射对象
     * @return 领域对象
     */
    MallData convert(MallDO mallDO);

    /**
     * @param tagData ProductTagData
     * @return ProductTagDO
     */
    @DataToDoIgnore
    ProductTagDO convert(ProductTagData tagData);

    /**
     * @param tagDo ProductTagDO
     * @return ProductTagData
     */
    ProductTagData convert(ProductTagDO tagDo);

    /**
     * @param brandData ProductBrandData
     * @return ProductBrandDO
     */
    ProductBrandDO convert(ProductBrandData brandData);

    /**
     * @param brandDo ProductBrandDO
     * @return ProductBrandData
     */
    ProductBrandData convert(ProductBrandDO brandDo);

    /**
     * @param qualityData ProductQualityData
     * @return ProductQualityDO
     */
    ProductQualityDO convert(ProductQualityData qualityData);

    /**
     * @param qualityDo ProductQualityDO
     * @return ProductQualityData
     */
    ProductQualityData convert(ProductQualityDO qualityDo);

    /**
     * @param data ProductCategoryData
     * @return ProductCategoryDO
     */
    ProductCategoryDO convert(ProductCategoryData data);

    /**
     * @param categoryDO ProductCategoryDO
     * @return ProductCategoryData
     */
    ProductCategoryData convert(ProductCategoryDO categoryDO);

    /**
     * @param data MallBankAccountData
     * @return MallBankAccountDO
     */
    MallBankAccountDO convert(MallBankAccountData data);

    /**
     * @param accountDO MallBankAccountDO
     * @return MallBankAccountData
     */
    MallBankAccountData convert(MallBankAccountDO accountDO);

    /**
     * @param data SupplierData
     * @return SupplierDO
     */
    SupplierDO convert(SupplierData data);

    /**
     * @param supplierDO SupplierDO
     * @return SupplierData
     */
    SupplierData convert(SupplierDO supplierDO);

    /**
     * @param data MallSapData
     * @return MallSapDO
     */
    MallSapDO convert(MallSapData data);

    /**
     * @param sapDO MallSapDO
     * @return MallSapData
     */
    MallSapData convert(MallSapDO sapDO);

    ShopData convert(ShopDO shopDO);

    /**
     * @param data ProductTagRelData
     * @return ProductTagRelDO
     */
    ProductTagRelDO convert(ProductTagRelData data);

    /**
     * @param tagRelDO ProductTagRelDO
     * @return ProductTagRelData
     */
    ProductTagRelData convert(ProductTagRelDO tagRelDO);

    ProvinceCityAreaData convert(ProvinceCityAreaDO data);

    /**
     * 转换
     *
     * @param saleOrderFlowDO SaleOrderFlowDO
     * @return SaleOrderFlowData
     */
    SaleOrderFlowData convert(SaleOrderFlowDO saleOrderFlowDO);

    /**
     * 转换
     *
     * @param saleOrderFlowData SaleOrderFlowData
     * @return SaleOrderFlowDO
     */
    @DataToDoIgnore
    SaleOrderFlowDO convert(SaleOrderFlowData saleOrderFlowData);
    /**
     * 转换
     *
     * @param approveDO SaleOrderPayApproveDO
     * @return SaleOrderPayApproveData
     */
    SaleOrderPayApproveData convert(SaleOrderPayApproveDO approveDO);

    /**
     * 转换
     *
     * @param data SaleOrderPayApproveData
     * @return SaleOrderPayApproveDO
     */
    @DataToDoIgnore
    SaleOrderPayApproveDO convert(SaleOrderPayApproveData data);
    /**
     * 转换
     *
     * @param relDO BatchPayFailRelDO
     * @return BatchPayFailRelData
     */
    BatchPayFailRelData convert(BatchPayFailRelDO relDO);

    /**
     * 转换
     *
     * @param data BatchPayFailRelData
     * @return BatchPayFailRelDO
     */
    @DataToDoIgnore
    BatchPayFailRelDO convert(BatchPayFailRelData data);
}
