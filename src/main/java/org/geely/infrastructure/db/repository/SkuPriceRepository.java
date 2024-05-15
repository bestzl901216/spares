package org.geely.infrastructure.db.repository;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.geely.domain.core.data.SkuPriceData;
import org.geely.infrastructure.db.SkuPriceDO;
import org.geely.infrastructure.db.convert.CoreDomainDataConvert;
import org.geely.infrastructure.db.service.SkuPriceDbService;
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
public class SkuPriceRepository {
    @Resource
    private SkuPriceDbService service;

    public SkuPriceData save(SkuPriceData data) {
        SkuPriceDO targetDO = CoreDomainDataConvert.INSTANCE.convert(data);
        boolean result = service.saveOrUpdate(targetDO);
        Assert.isTrue(result, "SKU渠道价格保存失败");
        return CoreDomainDataConvert.INSTANCE.convert(targetDO);
    }

    public List<SkuPriceData> listBySkuIds(Set<Integer> skuIds) {
        if(skuIds == null || skuIds.isEmpty()) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<SkuPriceDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SkuPriceDO::getSkuId, skuIds);
        return service.list(wrapper).stream().map(CoreDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }

    public void delete(SkuPriceData data) {
        if(data == null || data.getId() == null) {
            return;
        }
        service.removeById(data.getId());
    }
}
