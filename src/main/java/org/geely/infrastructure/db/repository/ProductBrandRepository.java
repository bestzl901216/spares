package org.geely.infrastructure.db.repository;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import org.geely.controller.dto.ProductBrandDTO;
import org.geely.domain.core.data.ProductBrandData;
import org.geely.infrastructure.db.ProductBrandDO;
import org.geely.infrastructure.db.convert.SupportDomainDataConvert;
import org.geely.infrastructure.db.convert.SupportDomainDtoConvert;
import org.geely.infrastructure.db.service.ProductBrandDbService;
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
public class ProductBrandRepository {
    @Resource
    private ProductBrandDbService service;

    public ProductBrandData save(ProductBrandData data) {
        Assert.isFalse(exist(data), "品牌已存在！");
        ProductBrandDO targetDO = SupportDomainDataConvert.INSTANCE.convert(data);
        boolean result = service.saveOrUpdate(targetDO);
        Assert.isTrue(result, "商品品牌数据保存失败");
        return SupportDomainDataConvert.INSTANCE.convert(targetDO);
    }

    public void delete(ProductBrandData data) {
        Assert.notNull(data, "商品品牌不存在");
        boolean result = service.removeById(data.getId());
        Assert.isTrue(result, "商品品牌数据删除失败");
    }

    public ProductBrandData getById(Integer id) {
        ProductBrandDO result = service.getById(id);
        Assert.notNull(result, "商品品牌不存在");
        return  SupportDomainDataConvert.INSTANCE.convert(result);
    }

    public Page<ProductBrandDTO> page(Long current, Long size, Integer mallId) {
        LambdaQueryWrapper<ProductBrandDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(ProductBrandDO::getId);
        wrapper.eq(ProductBrandDO::getMallId, mallId);
        Page<ProductBrandDO> page = service.page(new Page<>(current, size), wrapper);
        Page<ProductBrandDTO> result = PageDTO.of(current, size, page.getTotal());
        result.setRecords(page.getRecords().stream().map(SupportDomainDtoConvert.INSTANCE::convert).collect(Collectors.toList()));
        return result;
    }

    public List<ProductBrandData> listByMallId(Integer mallId) {
        LambdaQueryWrapper<ProductBrandDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(ProductBrandDO::getSort);
        wrapper.eq(ProductBrandDO::getMallId, mallId);
        return service.list(wrapper).stream().map(SupportDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }
    public boolean exist(ProductBrandData data){
        LambdaQueryWrapper<ProductBrandDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductBrandDO::getMallId, data.getMallId()).eq(ProductBrandDO::getName, data.getName());
        if(data.getId() != null && !data.getId().equals(0)) {
            wrapper.not(x->x.eq(ProductBrandDO::getId, data.getId()));
        }
        long count = service.count(wrapper);
        return  count > 0;
    }

    public List<ProductBrandData> listByIds(Set<Integer> ids) {
        if(ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }
        return service.listByIds(ids).stream().map(SupportDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }
}
