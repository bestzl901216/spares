package org.geely.infrastructure.db.convert;

import org.geely.controller.dto.*;
import org.geely.controller.dto.site.BankAccountDTO;
import org.geely.domain.common.data.MallBankAccountData;
import org.geely.domain.common.data.MarketChannelRelData;
import org.geely.domain.core.data.ProductPropsData;
import org.geely.domain.core.data.ProductTagData;
import org.geely.infrastructure.db.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ricardo zhou
 */
@Mapper
public interface SupportDomainDtoConvert {
    SupportDomainDtoConvert INSTANCE = Mappers.getMapper(SupportDomainDtoConvert.class);
    /**
     * @param tagDo ProductTagDO
     * @return ProductTagDTO
     */
    ProductTagPageDTO convert(ProductTagDO tagDo);
    /**
     * @param brandDO ProductBrandDO
     * @return ProductBrandDTO
     */
    ProductBrandDTO convert(ProductBrandDO brandDO);
    /**
     * @param qualityDO ProductQualityDO
     * @return ProductQualityDTO
     */
    ProductQualityDTO convert(ProductQualityDO qualityDO);

    /**
     * @param categoryDO ProductCategoryDO
     * @return ProductCategoryDTO
     */
    ProductCategoryDTO convert(ProductCategoryDO categoryDO);
    /**
     * @param bankAccountDO MallBankAccountDO
     * @return MallBankAccountDTO
     */
    MallBankAccountDTO convert(MallBankAccountDO bankAccountDO);
    /**
     * @param data MallBankAccountData
     * @return BankAccountDTO
     */
    BankAccountDTO convert(MallBankAccountData data);
    /**
     * @param sapDO MallSapDO
     * @return MallSapDTO
     */
    MallSapDTO convert(MallSapDO sapDO);
    /**
     * @param data ProductTagData
     * @return ProductTagDTO
     */
    ProductTagDTO convert(ProductTagData data);
    /**
     * @param data ProductPropsData
     * @return ProductPropsDO
     */
    ProductPropsDTO convert(ProductPropsData data);
    /**
     * @param data MarketChannelRelData
     * @return MarketChannelRelDTO
     */
    MarketChannelRelDTO convert(MarketChannelRelData data);
    /**
     * @param data HomePageSkuListDTO
     * @return HomePageSkuDTO
     */
    HomePageSkuDTO convert(HomePageSkuListDTO data);
}
