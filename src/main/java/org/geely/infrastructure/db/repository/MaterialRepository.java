package org.geely.infrastructure.db.repository;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.geely.domain.core.data.MaterialData;
import org.geely.infrastructure.db.MaterialDO;
import org.geely.infrastructure.db.MaterialShopRelDO;
import org.geely.infrastructure.db.convert.CoreDomainDataConvert;
import org.geely.infrastructure.db.service.MaterialDbService;
import org.geely.infrastructure.db.service.MaterialShopRelDbService;
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
public class MaterialRepository {
    @Resource
    private MaterialDbService service;
    @Resource
    private MaterialShopRelDbService relDbService;

    public MaterialData getById(Integer id) {
        MaterialDO result = service.getById(id);
        Assert.notNull(result, "物料不存在");
        return  CoreDomainDataConvert.INSTANCE.convert(result);
    }
    public boolean existRel(Integer materialId, Integer shopId) {
        LambdaQueryWrapper<MaterialShopRelDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MaterialShopRelDO::getMaterialId, materialId).eq(MaterialShopRelDO::getShopId,shopId).last("limit 1");
        List<MaterialShopRelDO> list = relDbService.list(wrapper);
        return !list.isEmpty();
    }

    public boolean allExistRel(Set<Integer> materialIds, Integer shopId) {
        if(materialIds == null || shopId == null || shopId.equals(0) || materialIds.isEmpty()) {
            return  false;
        }
        LambdaQueryWrapper<MaterialShopRelDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(MaterialShopRelDO::getMaterialId, materialIds).eq(MaterialShopRelDO::getShopId,shopId);
        List<MaterialShopRelDO> list = relDbService.list(wrapper);
        return list.size() == materialIds.size();
    }
    public List<MaterialData> listByIds(Set<Integer> ids) {
        if(ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }
        List<MaterialDO> dos = service.listByIds(ids);
        Assert.isTrue(ids.size() == dos.size(), "包含不存在的物料id");
        return dos.stream().map(CoreDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }

}
