package org.geely.infrastructure.db.convert;

import org.geely.controller.dto.*;
import org.geely.controller.dto.site.*;
import org.geely.domain.core.data.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author cong huang
 */
@Mapper
public interface SiteDtoConvert {
    SiteDtoConvert INSTANCE = Mappers.getMapper(SiteDtoConvert.class);
    /**
     * @param data MaterialData
     * @return MaterialSiteDTO
     */
    MaterialSiteDTO convert(MaterialData data);
    /**
     * @param data SkuData
     * @return SkuSiteDTO
     */
    SkuSiteDTO convert(SkuData data);
    /**
     * @param data CartSkuListDTO
     * @return CartSkuDTO
     */
    CartSkuDTO convert(CartShopSkuDTO data);
    /**
     * @param data SkuShopOrderingDTO
     * @return SkuOrderingDTO
     */
    SkuOrderingDTO convert(SkuShopOrderingDTO data);
}
