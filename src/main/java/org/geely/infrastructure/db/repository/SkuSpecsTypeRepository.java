package org.geely.infrastructure.db.repository;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.geely.domain.core.data.SkuSpecsTypeData;
import org.geely.infrastructure.db.SkuSpecsTypeDO;
import org.geely.infrastructure.db.convert.CoreDomainDataConvert;
import org.geely.infrastructure.db.service.SkuSpecsTypeDbService;
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
public class SkuSpecsTypeRepository {
    @Resource
    private SkuSpecsTypeDbService service;

    public SkuSpecsTypeData getById(Integer id) {
        return CoreDomainDataConvert.INSTANCE.convert(service.getById(id));
    }

    public SkuSpecsTypeData save(SkuSpecsTypeData data) {
        SkuSpecsTypeDO targetDO = CoreDomainDataConvert.INSTANCE.convert(data);
        boolean result = service.save(targetDO);
        Assert.isTrue(result, "规格类型保存失败");
        return CoreDomainDataConvert.INSTANCE.convert(targetDO);
    }

    public List<SkuSpecsTypeData> listByIds(Set<Integer> specsIds) {
        if (specsIds == null || specsIds.isEmpty()) {
            return new ArrayList<>();
        }
        List<SkuSpecsTypeDO> doList = service.listByIds(specsIds);
        Assert.isTrue(doList.size() == specsIds.size(), "包含不存在的规格类型id");
        return doList.stream().map(CoreDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }

    public List<SkuSpecsTypeData> listByMallId(Integer mallId) {
        Assert.notNull(mallId);
        LambdaQueryWrapper<SkuSpecsTypeDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SkuSpecsTypeDO::getMallId,mallId).orderByAsc(SkuSpecsTypeDO::getSort);
        return  service.list(wrapper).stream().map(CoreDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }
}
