package org.geely.domain.core;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.geely.domain.core.data.ProductPropsData;
import org.geely.infrastructure.db.repository.ProductPropsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品属性
 *
 * @author cong huang
 */
public class ProductProps {
    private ProductPropsData data;

    private ProductProps(ProductPropsData productTagData) {
        this.data = ObjectUtil.clone(productTagData);
    }

    public static List<ProductProps> newInstances(List<ProductPropsData> list) {
        if(list == null || list.isEmpty()) {
            return  new ArrayList<>();
        }
        for (ProductPropsData data : list) {
            data.setId(null);
            data.setVersion(0);
        }
        list = saveBatch(list);
        return list.stream().map(ProductProps::new).collect(Collectors.toList());
    }

    public static List<ProductProps> instancesOf(Integer productId) {
        List<ProductPropsData> list = SpringUtil.getBean(ProductPropsRepository.class).listByProductId(productId);
        return list.stream().map(ProductProps::new).collect(Collectors.toList());
    }

    private static List<ProductPropsData> saveBatch(List<ProductPropsData> list) {
        return SpringUtil.getBean(ProductPropsRepository.class).saveBatch(list);
    }

    public void delete() {
        SpringUtil.getBean(ProductPropsRepository.class).delete(this.data);
        data = null;
    }

    public ProductPropsData getData() {
        return ObjectUtil.clone(this.data);
    }

    public boolean update(ProductPropsData newData) {
        if(newData == null) {
            return false;
        }
        boolean changed = false;
        if(!this.data.getSort().equals(newData.getSort())) {
            changed = true;
            this.data.setSort(newData.getSort());
        }
        if(!this.data.getName().equals(newData.getName())) {
            changed = true;
            this.data.setName(newData.getName());
        }
        if(!this.data.getValue().equals(newData.getValue())) {
            changed = true;
            this.data.setValue(newData.getValue());
        }
        if(changed) {
            this.data = SpringUtil.getBean(ProductPropsRepository.class).save(this.data);
        }
        return changed;
    }

    public Integer getId() {
        return data.getId();
    }

    public Integer getSort() {
        return data.getSort();
    }
}
