package org.geely.infrastructure.db.repository;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.geely.common.enums.EnableStateEnum;
import org.geely.domain.core.data.SkuData;
import org.geely.domain.core.data.SkuSpecsData;
import org.geely.domain.core.data.SkuSpecsRelData;
import org.geely.infrastructure.db.*;
import org.geely.infrastructure.db.convert.CoreDomainDataConvert;
import org.geely.infrastructure.db.service.SkuDbService;
import org.geely.infrastructure.db.service.SkuImageDbService;
import org.geely.infrastructure.db.service.SkuSpecsRelDbService;
import org.geely.infrastructure.db.service.SkuSpecsTypeDbService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author cong huang
 */
@Repository
public class SkuRepository {
    @Resource
    private SkuDbService skuService;
    @Resource
    private SkuImageDbService imageService;
    @Resource
    private SkuSpecsRelDbService specsRelDbService;
    @Resource
    private SkuSpecsTypeDbService specsTypeDbService;

    public SkuData save(SkuData data) {
        SkuDO newDO = CoreDomainDataConvert.INSTANCE.convert(data);
        boolean result = skuService.saveOrUpdate(newDO);
        Assert.isTrue(result, "SKU保存失败");
        return CoreDomainDataConvert.INSTANCE.convert(newDO);
    }

    public void saveSpecs(List<SkuSpecsRelData> dataList) {
        if(dataList == null || dataList.isEmpty()) {
            return;
        }
        List<SkuSpecsRelDO> doList = dataList.stream().map(CoreDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
        specsRelDbService.saveBatch(doList);
    }

    public List<SkuData> listByProductId(Integer productId, boolean onlyEnable) {
        LambdaQueryWrapper<SkuDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SkuDO::getProductId, productId);
        if(onlyEnable) {
            wrapper.eq(SkuDO::getState, EnableStateEnum.ENABLE);
        }
        wrapper.orderByAsc(SkuDO::getSort);
        return skuService.list(wrapper).stream().map(CoreDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }

    public Map<Integer, List<SkuSpecsData>> listSpecsBySkuIds(Set<Integer> skuIds) {
        if (skuIds == null || skuIds.isEmpty()) {
            return new HashMap<>();
        }
        LambdaQueryWrapper<SkuSpecsRelDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SkuSpecsRelDO::getSkuId, skuIds);
        List<SkuSpecsRelDO> doList = specsRelDbService.list(wrapper);
        Set<Integer> specsTypeIds = doList.stream().map(SkuSpecsRelDO::getSpecsTypeId).collect(Collectors.toSet());
        Map<Integer, String> specsTypeMap = specsTypeDbService.listByIds(specsTypeIds).stream().collect(Collectors.toMap(BaseDO::getId, SkuSpecsTypeDO::getName));
        return doList.stream().collect(Collectors.groupingBy(SkuSpecsRelDO::getSkuId, Collectors.collectingAndThen(Collectors.toList(), x -> x.stream().map(y -> new SkuSpecsData(y.getSpecsTypeId(), y.getSpecs(), specsTypeMap.get(y.getSpecsTypeId()))).collect(Collectors.toList()))));
    }

    public void removeImages(Integer skuId) {
        LambdaQueryWrapper<SkuImageDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SkuImageDO::getSkuId, skuId);
        imageService.remove(wrapper);
    }

    public void removeSpecs(Integer skuId) {
        LambdaUpdateWrapper<SkuSpecsRelDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SkuSpecsRelDO::getSkuId, skuId);
        wrapper.setSql("deleted_flag = id");
        specsRelDbService.update(wrapper);
    }

    public void delete(Integer id) {
        skuService.removeById(id);
    }

    public SkuData getById(Integer skuId) {
        SkuDO skuDO = skuService.getById(skuId);
        Assert.notNull(skuDO, "该skuId不存在");
        return CoreDomainDataConvert.INSTANCE.convert(skuDO);
    }

    public List<SkuData> getByIds(Set<Integer> skuSet) {
        if(skuSet == null || skuSet.isEmpty()) {
            return new ArrayList<>();
        }
        List<SkuDO> skuDOS = skuService.listByIds(skuSet);
        return skuDOS.stream().map(CoreDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }
}
