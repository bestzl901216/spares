package org.geely.infrastructure.db.repository;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import org.geely.controller.dto.ProductQualityDTO;
import org.geely.domain.core.data.ProductQualityData;
import org.geely.infrastructure.db.ProductQualityDO;
import org.geely.infrastructure.db.convert.SupportDomainDataConvert;
import org.geely.infrastructure.db.convert.SupportDomainDtoConvert;
import org.geely.infrastructure.db.service.ProductQualityDbService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cong huang
 */
@Repository
public class ProductQualityRepository {
    @Resource
    private ProductQualityDbService service;

    public ProductQualityData save(ProductQualityData data) {
        Assert.isFalse(exist(data), "品质已存在！");
        ProductQualityDO qualityDO = SupportDomainDataConvert.INSTANCE.convert(data);
        boolean result = service.saveOrUpdate(qualityDO);
        Assert.isTrue(result, "商品品质数据保存失败");
        return SupportDomainDataConvert.INSTANCE.convert(qualityDO);
    }

    public void delete(ProductQualityData data) {
        Assert.notNull(data, "商品品质不存在");
        boolean result = service.removeById(data.getId());
        Assert.isTrue(result, "商品品质数据删除失败");
    }

    public ProductQualityData getById(Integer id) {
        ProductQualityDO result = service.getById(id);
        Assert.notNull(result, "商品品质不存在");
        return  SupportDomainDataConvert.INSTANCE.convert(result);
    }

    public Page<ProductQualityDTO> page(long current, long size, Integer mallId)
    {
        LambdaQueryWrapper<ProductQualityDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(ProductQualityDO::getId);
        wrapper.eq(ProductQualityDO::getMallId, mallId);
        Page<ProductQualityDO> page = service.page(new Page<>(current, size), wrapper);
        Page<ProductQualityDTO> result = PageDTO.of(current, size, page.getTotal());
        result.setRecords(page.getRecords().stream().map(SupportDomainDtoConvert.INSTANCE::convert).collect(Collectors.toList()));
        return result;
    }
    public boolean exist(ProductQualityData data){
        LambdaQueryWrapper<ProductQualityDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductQualityDO::getMallId, data.getMallId());
        wrapper.eq(ProductQualityDO::getName, data.getName());
        if(data.getId() != null && !data.getId().equals(0)) {
            wrapper.not(x->x.eq(ProductQualityDO::getId, data.getId()));
        }
        long count = service.count(wrapper);
        return  count > 0;
    }

    public List<ProductQualityData> listByMallId(Integer mallId) {
        LambdaQueryWrapper<ProductQualityDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(ProductQualityDO::getSort);
        wrapper.eq(ProductQualityDO::getMallId, mallId);
        return service.list(wrapper).stream().map(SupportDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }
}
