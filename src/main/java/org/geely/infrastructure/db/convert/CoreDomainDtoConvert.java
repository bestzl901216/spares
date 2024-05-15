package org.geely.infrastructure.db.convert;

import org.geely.controller.dto.*;
import org.geely.domain.core.data.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author cong huang
 */
@Mapper
public interface CoreDomainDtoConvert {
    CoreDomainDtoConvert INSTANCE = Mappers.getMapper(CoreDomainDtoConvert.class);

    /**
     * @param data ProductData
     * @return ProductDTO
     */
    @Mapping(target = "state", expression = "java(data.getState().getId())")
    ProductDTO convert(ProductData data);

    /**
     * @param data SkuData
     * @return SkuDTO
     */
    SkuDTO convert(SkuData data);

    /**
     * @param data MaterialData
     * @return MaterialDTO
     */
    MaterialDTO convert(MaterialData data);

    /**
     * @param data SkuSpecsData
     * @return SkuSpecsDTO
     */
    SkuSpecsDTO convert(SkuSpecsData data);

    /**
     * @param data SkuPriceData
     * @return SkuPriceDTO
     */
    SkuPriceDTO convert(SkuPriceData data);

    CustomerAddressDTO convert(CustomerAddressData data);
}
