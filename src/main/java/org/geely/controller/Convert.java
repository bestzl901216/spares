package org.geely.controller;

import org.geely.common.annotation.DtoToDataIgnore;
import org.geely.controller.dto.*;
import org.geely.domain.common.data.AccountData;
import org.geely.domain.common.data.MarketChannelData;
import org.geely.domain.core.data.CustomerData;
import org.geely.domain.core.data.HomePageSkuData;
import org.geely.domain.core.data.ProductTagData;
import org.geely.domain.support.data.RoleData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author ricardo zhou
 */
@Mapper
public interface Convert {
    Convert INSTANCE = Mappers.getMapper(Convert.class);

    /**
     * 转换
     *
     * @param accountCreateDTO accountDTO
     * @return AccountData
     */
    @DtoToDataIgnore
    @Mapping(target = "encodedPassword", ignore = true)
    @Mapping(target = "name", source = "accountCreateDTO.phone")
    AccountData convert(AccountCreateDTO accountCreateDTO);

    /**
     * 转换
     *
     * @param marketChannelCreateDTO marketChannelDTO
     * @return marketChannelData
     */
    @DtoToDataIgnore
    MarketChannelData convert(MarketChannelCreateDTO marketChannelCreateDTO);

    /**
     * 转换
     *
     * @param customerCreateDTO customerCreateDTO
     * @return customerData
     */
    @DtoToDataIgnore
    @Mapping(target = "payPassword", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "certifyState", ignore = true)
    CustomerData convert(CustomerCreateDTO customerCreateDTO);

    /**
     * @param dto ProductTagCreateDTO
     * @return ProductTagData
     */
    @DtoToDataIgnore
    ProductTagData convert(ProductTagCreateDTO dto);

    @DtoToDataIgnore
    @Mapping(target = "platformType", ignore = true)
    @Mapping(target = "platformId", ignore = true)
    RoleData convert(RoleCreateDTO roleCreateDTO);

    HomePageSkuData convert(HomePageSkuUpdateDTO dto);
}
