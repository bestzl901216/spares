package org.geely.domain.core;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.geely.domain.core.data.SkuImageData;
import org.geely.infrastructure.db.repository.SkuImageRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * SKU图片
 *
 * @author cong huang
 */
public class SkuImage {
    private SkuImageData data;

    private SkuImage(SkuImageData productTagData) {
        this.data = ObjectUtil.clone(productTagData);
    }

    public static List<SkuImage> newInstances(List<SkuImageData> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        for (SkuImageData d : list) {
            d.setId(null);
            d.setVersion(0);
        }
        list = saveBatch(list);
        return list.stream().map(SkuImage::new).collect(Collectors.toList());
    }

    public static Map<Integer, List<SkuImage>> instancesOf(Set<Integer> skuIds) {
        List<SkuImageData> list = SpringUtil.getBean(SkuImageRepository.class).listBySkuIds(skuIds);
        return list.stream().collect(Collectors.groupingBy(SkuImageData::getSkuId, Collectors.collectingAndThen(Collectors.toList(), x -> x.stream().sorted(Comparator.comparing(SkuImageData::getSort)).map(SkuImage::new).collect(Collectors.toList()))));
    }

    private static List<SkuImageData> saveBatch(List<SkuImageData> list) {
        return SpringUtil.getBean(SkuImageRepository.class).saveBatch(list);
    }

    public void delete() {
        SpringUtil.getBean(SkuImageRepository.class).delete(this.data);
        data = null;
    }

    public SkuImageData getData() {
        return ObjectUtil.clone(this.data);
    }

    public boolean updateSort(Integer newSort) {
        if (newSort == null) {
            newSort = 0;
        }
        if (data.getSort().equals(newSort)) {
            return false;
        }
        this.data.setSort(newSort);
        this.data = SpringUtil.getBean(SkuImageRepository.class).save(this.data);
        return true;
    }

    public String getUrl() {
        return this.data.getUrl();
    }

    public Integer getSort() {
        return data.getSort();
    }
}
