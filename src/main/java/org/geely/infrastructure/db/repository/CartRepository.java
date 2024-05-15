package org.geely.infrastructure.db.repository;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.geely.domain.core.data.CartData;
import org.geely.infrastructure.db.CartDO;
import org.geely.infrastructure.db.convert.CoreDomainDataConvert;
import org.geely.infrastructure.db.service.CartDbService;
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
public class CartRepository {
    @Resource
    private CartDbService service;

    public CartData save(CartData data) {
        Assert.notNull(data);
        CartDO aDo = CoreDomainDataConvert.INSTANCE.convert(data);
        boolean result = service.saveOrUpdate(aDo);
        Assert.isTrue(result, "购物车数据保存失败");
        return CoreDomainDataConvert.INSTANCE.convert(aDo);
    }

    public CartData getById(Integer id) {
        CartDO result = service.getById(id);
        Assert.notNull(result, "不存在的购物车数据");
        return CoreDomainDataConvert.INSTANCE.convert(result);
    }

    public void delete(CartData data) {
        Assert.notNull(data, "购物车数据不存在");
        service.removeById(data.getId());
    }

    public List<CartData> listByIds(Set<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }
        List<CartDO> dos = service.listByIds(ids);
        Assert.isTrue(dos.size() == ids.size(), "包含不正确的购物车id");
        return dos.stream().map(CoreDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }

    public CartData get(CartData data) {
        Assert.notNull(data);
        Assert.notNull(data.getMallId());
        Assert.notNull(data.getCustomerId());
        Assert.notNull(data.getAccountId());
        Assert.notNull(data.getSkuId());
        LambdaQueryWrapper<CartDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CartDO::getMallId, data.getMallId())
                .eq(CartDO::getCustomerId, data.getCustomerId())
                .eq(CartDO::getAccountId, data.getAccountId())
                .eq(CartDO::getSkuId, data.getSkuId());
        List<CartDO> list = service.list(wrapper);
        if (list.isEmpty()) {
            return null;
        } else {
            return CoreDomainDataConvert.INSTANCE.convert(list.get(0));
        }
    }

    public Long count(Integer mallId, Integer customerId, Integer accountId) {
        Assert.notNull(mallId);
        Assert.notNull(customerId);
        Assert.notNull(accountId);
        LambdaQueryWrapper<CartDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CartDO::getMallId, mallId)
                .eq(CartDO::getCustomerId, customerId)
                .eq(CartDO::getAccountId, accountId);
        return service.count(wrapper);
    }

    public List<CartData> listBySkuIds(Integer mallId, Integer customerId, Integer accountId, Set<Integer> skuSet) {
        Assert.notNull(mallId);
        Assert.notNull(customerId);
        Assert.notNull(accountId);
        Assert.notNull(skuSet);
        Assert.isFalse(skuSet.isEmpty());
        LambdaQueryWrapper<CartDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CartDO::getMallId, mallId)
                .eq(CartDO::getCustomerId, customerId)
                .eq(CartDO::getAccountId, accountId)
                .in(CartDO::getSkuId, skuSet);
        return service.list(wrapper).stream().map(CoreDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }
}
