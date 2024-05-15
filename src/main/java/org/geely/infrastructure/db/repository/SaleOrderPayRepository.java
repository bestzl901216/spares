package org.geely.infrastructure.db.repository;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.geely.domain.core.data.SaleOrderPayData;
import org.geely.infrastructure.db.SaleOrderPayDO;
import org.geely.infrastructure.db.convert.CoreDomainDataConvert;
import org.geely.infrastructure.db.service.SaleOrderPayDbService;
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
public class SaleOrderPayRepository {
    @Resource
    private SaleOrderPayDbService service;

    public SaleOrderPayData save(SaleOrderPayData data) {
        SaleOrderPayDO thisDo = CoreDomainDataConvert.INSTANCE.convert(data);
        service.saveOrUpdate(thisDo);
        return CoreDomainDataConvert.INSTANCE.convert(thisDo);
    }

    public SaleOrderPayData getBySn(String sn) {
        LambdaQueryWrapper<SaleOrderPayDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SaleOrderPayDO::getSn, sn);
        return CoreDomainDataConvert.INSTANCE.convert(service.getOne(wrapper));
    }

    public List<SaleOrderPayData> getByParentId(Integer id) {
        LambdaQueryWrapper<SaleOrderPayDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SaleOrderPayDO::getParentId, id);
        return service.list(wrapper).stream().map(CoreDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }

    public List<SaleOrderPayData> listByOrderIds(Set<Integer> orderIds) {
        if(orderIds == null || orderIds.isEmpty()) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<SaleOrderPayDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SaleOrderPayDO::getSaleOrderId, orderIds);
        List<SaleOrderPayDO> list = service.list(wrapper);
        Assert.isTrue(list.size() == orderIds.size(), "有支付单不存在");
        return list.stream().map(CoreDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }

    public List<SaleOrderPayData> listByParentIds(Set<Integer> parentIds) {
        if(parentIds == null || parentIds.isEmpty()) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<SaleOrderPayDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SaleOrderPayDO::getParentId, parentIds);
        List<SaleOrderPayDO> list = service.list(wrapper);
        return list.stream().map(CoreDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }

    public List<SaleOrderPayData> listByIds(Set<Integer> ids) {
        if(ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }
        List<SaleOrderPayDO> list = service.listByIds(ids);
        Assert.isTrue(list.size() == ids.size(), "有支付单不存在");
        return list.stream().map(CoreDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }

    public void delete(SaleOrderPayData data) {
        Assert.notNull(data);
        service.removeById(data.getId());
    }
}
