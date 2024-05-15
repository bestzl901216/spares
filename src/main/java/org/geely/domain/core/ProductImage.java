package org.geely.domain.core;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.geely.domain.core.data.ProductImageData;
import org.geely.infrastructure.db.repository.ProductImageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品详情图片
 *
 * @author cong huang
 */
public class ProductImage {
    private ProductImageData data;

    private ProductImage(ProductImageData productTagData) {
        this.data = ObjectUtil.clone(productTagData);
    }

    public static List<ProductImage> newInstances(List<ProductImageData> list) {
        if(list == null || list.isEmpty()) {
            return  new ArrayList<>();
        }
        for (ProductImageData productImageData : list) {
            productImageData.setId(null);
            productImageData.setVersion(0);
        }
        list = saveBatch(list);
        return list.stream().map(ProductImage::new).collect(Collectors.toList());
    }

    public static List<ProductImage> instancesOf(Integer productId) {
        List<ProductImageData> list = SpringUtil.getBean(ProductImageRepository.class).listByProductId(productId);
        return list.stream().map(ProductImage::new).collect(Collectors.toList());
    }

    private static List<ProductImageData> saveBatch(List<ProductImageData> list) {
        return SpringUtil.getBean(ProductImageRepository.class).saveBatch(list);
    }

    public void delete() {
        SpringUtil.getBean(ProductImageRepository.class).delete(this.data);
        data = null;
    }

    public ProductImageData getData() {
        return ObjectUtil.clone(this.data);
    }

    public boolean updateSort(Integer newSort) {
        if(newSort == null) {
            newSort = 0;
        }
        
        if(data.getSort().equals(newSort)) {
            return false;
        }
        this.data.setSort(newSort);
        this.data = SpringUtil.getBean(ProductImageRepository.class).save(this.data);
        return true;
    }

    public String getUrl() {
        return this.data.getUrl();
    }

    public Integer getSort() {
        return data.getSort();
    }
}
