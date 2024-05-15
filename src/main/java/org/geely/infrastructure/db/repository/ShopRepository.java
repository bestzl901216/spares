package org.geely.infrastructure.db.repository;

import cn.hutool.core.lang.Assert;
import org.geely.domain.core.data.ShopData;
import org.geely.infrastructure.db.ShopDO;
import org.geely.infrastructure.db.convert.CoreDomainDataConvert;
import org.geely.infrastructure.db.service.ShopDbService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author cong huang
 */
@Repository
public class ShopRepository {
    @Resource
    private ShopDbService service;

    public ShopData save(ShopData data) {
        ShopDO shopDO = CoreDomainDataConvert.INSTANCE.convert(data);
        boolean result = service.saveOrUpdate(shopDO);
        Assert.isTrue(result, "店铺保存失败");
        return CoreDomainDataConvert.INSTANCE.convert(shopDO);
    }

    public ShopData getById(Integer id) {
        ShopDO result = service.getById(id);
        Assert.notNull(result, "店铺不存在");
        return CoreDomainDataConvert.INSTANCE.convert(result);
    }

    public List<ShopData> listByIds(Set<Integer> shopIds) {
        if(shopIds == null || shopIds.isEmpty()) {
            return new ArrayList<>();
        }
        List<ShopDO> shopDOList = service.listByIds(shopIds);
        Assert.isTrue(shopIds.size() == shopDOList.size(), "包含不存在的店铺id");
        return shopDOList.stream().map(CoreDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }
}
