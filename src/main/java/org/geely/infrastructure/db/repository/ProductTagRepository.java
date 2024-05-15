package org.geely.infrastructure.db.repository;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import org.geely.controller.dto.ProductTagPageDTO;
import org.geely.domain.core.data.ProductTagData;
import org.geely.infrastructure.db.ProductTagDO;
import org.geely.infrastructure.db.convert.SupportDomainDataConvert;
import org.geely.infrastructure.db.convert.SupportDomainDtoConvert;
import org.geely.infrastructure.db.service.ProductTagDbService;
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
public class ProductTagRepository {
    @Resource
    private ProductTagDbService service;

    public ProductTagData save(ProductTagData data) {
        Assert.isFalse(exist(data), "标签已存在！");
        ProductTagDO tagDo = SupportDomainDataConvert.INSTANCE.convert(data);
        boolean result = service.saveOrUpdate(tagDo);
        Assert.isTrue(result, "商品标签数据保存失败");
        return SupportDomainDataConvert.INSTANCE.convert(tagDo);
    }

    public void delete(ProductTagData data) {
        Assert.notNull(data, "商品标签不存在");
        boolean result = service.removeById(data.getId());
        Assert.isTrue(result, "商品标签数据删除失败");
    }

    public ProductTagData getById(Integer id) {
        ProductTagDO result = service.getById(id);
        Assert.notNull(result, "商品标签不存在");
        return  SupportDomainDataConvert.INSTANCE.convert(result);
    }

    public List<ProductTagData> listByIds(Set<Integer> ids) {
        if(ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }
        List<ProductTagDO> result = service.listByIds(ids);
        return result.stream().map(SupportDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }

    public Page<ProductTagPageDTO> page(long current, long size, Integer mallId)
    {
        LambdaQueryWrapper<ProductTagDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(ProductTagDO::getId);
        wrapper.eq(ProductTagDO::getMallId, mallId);
        Page<ProductTagDO> page = service.page(new Page<>(current, size), wrapper);
        Page<ProductTagPageDTO> result = PageDTO.of(current, size, page.getTotal());
        result.setRecords(page.getRecords().stream().map(SupportDomainDtoConvert.INSTANCE::convert).collect(Collectors.toList()));
        return result;
    }
    public boolean exist(ProductTagData data){
        LambdaQueryWrapper<ProductTagDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductTagDO::getMallId, data.getMallId()).eq(ProductTagDO::getName, data.getName());
        if(data.getId() != null && !data.getId().equals(0)) {
            wrapper.not(x->x.eq(ProductTagDO::getId, data.getId()));
        }
        long count = service.count(wrapper);
        return  count > 0;
    }

    public List<ProductTagData> listByMallId(Integer mallId) {
        LambdaQueryWrapper<ProductTagDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(ProductTagDO::getSort);
        wrapper.eq(ProductTagDO::getMallId, mallId);
        return service.list(wrapper).stream().map(SupportDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }
}
