package org.geely.domain.core;


import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.geely.controller.dto.ProductQualityDTO;
import org.geely.controller.dto.SelectorDTO;
import org.geely.domain.core.data.ProductQualityData;
import org.geely.infrastructure.db.repository.ProductQualityRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 租户
 *
 * @author cong huang
 */
public class ProductQuality {
    private ProductQualityData data;

    private ProductQuality(ProductQualityData data) {
        this.data = ObjectUtil.clone(data);
    }

    public static ProductQuality newInstance(ProductQualityData data) {
        ProductQuality quality = new ProductQuality(data);
        quality.data.setId(null);
        quality.data.setVersion(0);
        quality.save();
        return quality;
    }

    public static ProductQuality instanceOf(Integer id, Integer mallId) {
        ProductQualityData data = SpringUtil.getBean(ProductQualityRepository.class).getById(id);
        Assert.isTrue(data.getMallId().equals(mallId), "品质id不正确");
        return new ProductQuality(data);
    }

    public static Page<ProductQualityDTO> page(Long current, Long size, Integer mallId) {
        return SpringUtil.getBean(ProductQualityRepository.class).page(current,size, mallId);
    }

    public static List<ProductQuality> instancesOf(Integer mallId) {
        return SpringUtil.getBean(ProductQualityRepository.class).listByMallId(mallId).stream().map(ProductQuality::new).collect(Collectors.toList());
    }

    public void save() {

        this.data = SpringUtil.getBean(ProductQualityRepository.class).save(this.data);
    }

    public void delete() {
        SpringUtil.getBean(ProductQualityRepository.class).delete(this.data);
        data = null;
    }

    public ProductQualityData getData() {
        return ObjectUtil.clone(this.data);
    }

    public Integer getId() {
        return data.getId();
    }
    public SelectorDTO getSelector() {
        return new SelectorDTO(data.getName(), data.getId().toString());
    }

    public String getName() {
        return data.getName();
    }
}
