package org.geely.infrastructure.db.repository;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.geely.domain.core.data.*;
import org.geely.infrastructure.db.*;
import org.geely.infrastructure.db.convert.CoreDomainDataConvert;
import org.geely.infrastructure.db.convert.SupportDomainDataConvert;
import org.geely.infrastructure.db.service.*;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author cong huang
 */
@Repository
public class ProductRepository {
    @Resource
    private ProductDbService productService;
    @Resource
    private ProductTagRelDbService tagRelService;
    @Resource
    private ProductTagDbService tagService;

    public ProductData save(ProductData data) {
        ProductDO newDO = CoreDomainDataConvert.INSTANCE.convert(data);
        boolean result = productService.saveOrUpdate(newDO);
        Assert.isTrue(result, "商品保存失败");
        return CoreDomainDataConvert.INSTANCE.convert(newDO);
    }

    public ProductData getById(Integer id) {
        ProductDO result = productService.getById(id);
        Assert.notNull(result, "商品不存在");
        return CoreDomainDataConvert.INSTANCE.convert(result);
    }

    public void saveTags(List<ProductTagRelData> relDataList) {
        if(relDataList == null || relDataList.isEmpty()) {
            return;
        }
        List<ProductTagRelDO> doList = relDataList.stream().map(SupportDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
        tagRelService.saveBatch(doList);
    }

    public List<ProductTagData> getTagsByProductId(Integer productId) {
        LambdaQueryWrapper<ProductTagRelDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductTagRelDO::getProductId, productId);
        List<ProductTagRelDO> productTagRelDOs = tagRelService.list(wrapper);
        if(productTagRelDOs.isEmpty()){
            return  new ArrayList<>();
        }
        Set<Integer> tagIds = productTagRelDOs.stream().map(ProductTagRelDO::getTagId).collect(Collectors.toSet());
        return tagService.listByIds(tagIds).stream().sorted(Comparator.comparing(ProductTagDO::getId)).map(SupportDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }

    public boolean removeTags(Integer productId, Set<Integer> tagIds) {
        if(tagIds == null || tagIds.isEmpty()) {
            return false;
        }
        LambdaUpdateWrapper<ProductTagRelDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(ProductTagRelDO::getProductId, productId).in(ProductTagRelDO::getTagId, tagIds);
        wrapper.setSql("deleted_flag = id");
        return tagRelService.update(wrapper);
    }

    public List<ProductData> listByIds(Set<Integer> ids) {
        if(ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }
        List<ProductDO> doList = productService.listByIds(ids);
        Assert.isTrue(ids.size() == doList.size(), "包含不存在的商品id");
        return  doList.stream().map(CoreDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }
}