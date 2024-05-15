package org.geely.infrastructure.db.gateway;

import org.apache.ibatis.annotations.Mapper;
import org.geely.controller.dto.HomePageSkuListDTO;

import java.util.List;

/**
 * @author ricardo zhou
 */
@Mapper
public interface HomePageCategoryDbGateway {
    /**
     * 商城首页广告类目列表
     *
     * @param mallId 商城id
     * @return 商城首页广告位类目列表
     */
    List<HomePageSkuListDTO> homePageCategoryList(Integer mallId);
}
