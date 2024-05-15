package org.geely.infrastructure.db.repository;

import cn.hutool.core.lang.Assert;
import org.geely.domain.core.data.ProductTagRelData;
import org.geely.infrastructure.db.ProductTagRelDO;
import org.geely.infrastructure.db.convert.SupportDomainDataConvert;
import org.geely.infrastructure.db.service.ProductTagRelDbService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cong huang
 */
@Repository
public class ProductTagRelRepository {
    @Resource
    private ProductTagRelDbService service;

    public List<ProductTagRelData> saveBatch(List<ProductTagRelData> list) {
        List<ProductTagRelDO> tagDos = list.stream().map(SupportDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
        boolean result = service.saveBatch(tagDos);
        Assert.isTrue(result, "商品标签关联数据保存失败");
        return tagDos.stream().map(SupportDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }
}
