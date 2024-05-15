package org.geely.infrastructure.db.repository;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.geely.domain.core.data.SupplierData;
import org.geely.infrastructure.db.SupplierDO;
import org.geely.infrastructure.db.convert.SupportDomainDataConvert;
import org.geely.infrastructure.db.service.SupplierDbService;
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
public class SupplierRepository {
    @Resource
    private SupplierDbService service;

    public SupplierData save(SupplierData data) {
        Assert.isFalse(exist(data), "商家名称已存在！");
        SupplierDO supplierDO = SupportDomainDataConvert.INSTANCE.convert(data);
        boolean result = service.saveOrUpdate(supplierDO);
        Assert.isTrue(result, "商家保存失败");
        return SupportDomainDataConvert.INSTANCE.convert(supplierDO);
    }

    public SupplierData getById(Integer id) {
        SupplierDO result = service.getById(id);
        Assert.notNull(result, "商家不存在");
        return SupportDomainDataConvert.INSTANCE.convert(result);
    }
    public boolean exist(SupplierData data){
        LambdaQueryWrapper<SupplierDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SupplierDO::getMallId, data.getMallId()).eq(SupplierDO::getName, data.getName());
        if(data.getId() != null && !data.getId().equals(0)) {
            wrapper.not(x->x.eq(SupplierDO::getId, data.getId()));
        }
        long count = service.count(wrapper);
        return  count > 0;
    }

    public List<SupplierData> listByIds(Set<Integer> ids) {
        if(ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }
        List<SupplierDO> list = service.listByIds(ids);
        Assert.isTrue(list.size() == ids.size(), "包含不存在的商家id");
        return list.stream().map(SupportDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }
}
