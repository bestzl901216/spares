package org.geely.infrastructure.db.repository;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.geely.domain.core.data.ProductImageData;
import org.geely.infrastructure.db.ProductImageDO;
import org.geely.infrastructure.db.convert.CoreDomainDataConvert;
import org.geely.infrastructure.db.service.ProductImageDbService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品图片
 * @author cong huang
 */
@Repository
public class ProductImageRepository {
    @Resource
    private ProductImageDbService service;

    public List<ProductImageData> saveBatch(List<ProductImageData> list) {
        if(list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        List<ProductImageDO> dos = list.stream().map(CoreDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
        boolean result = service.saveBatch(dos);
        Assert.isTrue(result, "商品详情图片保存失败");
        return dos.stream().map(CoreDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }

    public List<ProductImageData> listByProductId(Integer productId) {
        LambdaQueryWrapper<ProductImageDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductImageDO::getProductId, productId).orderByAsc(ProductImageDO::getSort);
        return service.list(wrapper).stream().map((CoreDomainDataConvert.INSTANCE::convert)).collect(Collectors.toList());
    }

    public void delete(ProductImageData data) {
        if(data == null || data.getId() == null) {
            return;
        }
        service.removeById(data.getId());
    }

    public ProductImageData save(ProductImageData data) {
        ProductImageDO targetDO = CoreDomainDataConvert.INSTANCE.convert(data);
        boolean result = service.saveOrUpdate(targetDO);
        Assert.isTrue(result, "商品详情图片保存失败");
        return CoreDomainDataConvert.INSTANCE.convert(targetDO);
    }
}
