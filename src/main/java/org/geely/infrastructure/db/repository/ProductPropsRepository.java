package org.geely.infrastructure.db.repository;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.geely.domain.core.data.ProductPropsData;
import org.geely.infrastructure.db.ProductPropsDO;
import org.geely.infrastructure.db.convert.CoreDomainDataConvert;
import org.geely.infrastructure.db.service.ProductPropsDbService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cong huang
 */
@Repository
public class ProductPropsRepository {
    @Resource
    private ProductPropsDbService service;

    public List<ProductPropsData> saveBatch(List<ProductPropsData> list) {
        if(list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        List<ProductPropsDO> dos = list.stream().map(CoreDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
        boolean result = service.saveBatch(dos);
        Assert.isTrue(result, "商品属性保存失败");
        return dos.stream().map(CoreDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }

    public List<ProductPropsData> listByProductId(Integer productId) {
        LambdaQueryWrapper<ProductPropsDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductPropsDO::getProductId, productId).orderByAsc(ProductPropsDO::getSort);
        return service.list(wrapper).stream().map((CoreDomainDataConvert.INSTANCE::convert)).collect(Collectors.toList());
    }

    public void delete(ProductPropsData data) {
        if(data == null || data.getId() == null) {
            return;
        }
        service.removeById(data.getId());
    }

    public ProductPropsData save(ProductPropsData data) {
        ProductPropsDO targetDO = CoreDomainDataConvert.INSTANCE.convert(data);
        boolean result = service.saveOrUpdate(targetDO);
        Assert.isTrue(result, "商品属性保存失败");
        return CoreDomainDataConvert.INSTANCE.convert(targetDO);
    }
}
