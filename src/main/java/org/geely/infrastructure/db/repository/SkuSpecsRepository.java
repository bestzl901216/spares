package org.geely.infrastructure.db.repository;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.geely.domain.core.data.SkuSpecsData;
import org.geely.infrastructure.db.SkuSpecsDO;
import org.geely.infrastructure.db.convert.CoreDomainDataConvert;
import org.geely.infrastructure.db.service.SkuSpecsDbService;
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
public class SkuSpecsRepository {
    @Resource
    private SkuSpecsDbService service;

    public SkuSpecsData save(SkuSpecsData data) {
        SkuSpecsDO targetDO = CoreDomainDataConvert.INSTANCE.convert(data);
        boolean result = service.saveOrUpdate(targetDO);
        Assert.isTrue(result, "规格保存失败");
        return CoreDomainDataConvert.INSTANCE.convert(targetDO);
    }

    public SkuSpecsData getByName(Integer typeId, String name) {
        LambdaQueryWrapper<SkuSpecsDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SkuSpecsDO::getTypeId, typeId).eq(SkuSpecsDO::getName, name);
        List<SkuSpecsDO> result = service.list(wrapper);
        if (result.isEmpty()) {
            return null;
        }
        return CoreDomainDataConvert.INSTANCE.convert(result.get(0));
    }

    public List<SkuSpecsData> listByIds(Set<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }
        return service.listByIds(ids).stream().map(CoreDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }

    public List<SkuSpecsData> listByTypeId(Integer typeId) {
        LambdaQueryWrapper<SkuSpecsDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SkuSpecsDO::getTypeId, typeId).orderByAsc(SkuSpecsDO::getSort);
        return service.list(wrapper).stream().map(CoreDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }
}
