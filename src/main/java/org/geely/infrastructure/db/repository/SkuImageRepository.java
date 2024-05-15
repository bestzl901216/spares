package org.geely.infrastructure.db.repository;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.geely.domain.core.data.SkuImageData;
import org.geely.infrastructure.db.SkuImageDO;
import org.geely.infrastructure.db.convert.CoreDomainDataConvert;
import org.geely.infrastructure.db.service.SkuImageDbService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * SKU图片
 * @author cong huang
 */
@Repository
public class SkuImageRepository {
    @Resource
    private SkuImageDbService service;

    public List<SkuImageData> saveBatch(List<SkuImageData> list) {
        if(list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        List<SkuImageDO> dos = list.stream().map(CoreDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
        boolean result = service.saveBatch(dos);
        Assert.isTrue(result, "SKU图片保存失败");
        return dos.stream().map(CoreDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }

    public List<SkuImageData> listBySkuIds(Set<Integer> skuIds) {
        if(skuIds == null || skuIds.isEmpty()) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<SkuImageDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SkuImageDO::getSkuId, skuIds);
        return service.list(wrapper).stream().map((CoreDomainDataConvert.INSTANCE::convert)).collect(Collectors.toList());
    }

    public void delete(SkuImageData data) {
        if(data == null || data.getId() == null) {
            return;
        }
        service.removeById(data.getId());
    }

    public SkuImageData save(SkuImageData data) {
        SkuImageDO targetDO = CoreDomainDataConvert.INSTANCE.convert(data);
        boolean result = service.saveOrUpdate(targetDO);
        Assert.isTrue(result, "SKU图片保存失败");
        return CoreDomainDataConvert.INSTANCE.convert(targetDO);
    }
}
