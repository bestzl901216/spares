package org.geely.infrastructure.db.repository;

import cn.hutool.core.lang.Assert;
import org.geely.domain.core.data.HomePageSkuData;
import org.geely.infrastructure.db.HomePageSkuDO;
import org.geely.infrastructure.db.convert.CoreDomainDataConvert;
import org.geely.infrastructure.db.service.HomePageSkuDbService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author cong huang
 */
@Repository
public class HomePageSkuRepository {
    @Resource
    private HomePageSkuDbService service;

    @Transactional(rollbackFor = Exception.class)
    public HomePageSkuData save(HomePageSkuData data) {
        if (data.getId() != null && data.getId() > 0) {
            HomePageSkuDO existHomePageSku = service.getById(data.getId());
            Assert.notNull(existHomePageSku, "数据不存在");
            data.setVersion(existHomePageSku.getVersion());
        }
        HomePageSkuDO newDO = CoreDomainDataConvert.INSTANCE.convert(data);

        try {
            boolean result = service.saveOrUpdate(newDO);
            Assert.isTrue(result, "保存失败");
        } catch (DuplicateKeyException e) {
            Assert.isTrue(false, "数据重复请重新填写");
        }
        return CoreDomainDataConvert.INSTANCE.convert(newDO);
    }
}
