package org.geely.domain.core;


import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.geely.common.utils.OperatorUtil;
import org.geely.controller.dto.ProductBrandDTO;
import org.geely.controller.dto.SelectorImageDTO;
import org.geely.domain.core.data.ProductBrandData;
import org.geely.infrastructure.db.repository.ProductBrandRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 租户
 *
 * @author cong huang
 */
public class ProductBrand {
    private ProductBrandData data;

    private ProductBrand(ProductBrandData data) {
        this.data = ObjectUtil.clone(data);
    }

    public static ProductBrand newInstance(ProductBrandData data) {
        ProductBrand instance = new ProductBrand(data);
        instance.data.setId(null);
        instance.data.setVersion(0);
        instance.save();
        return instance;
    }
    public static ProductBrand instancesOf(Integer id, Integer mallId) {
        ProductBrandData data = SpringUtil.getBean(ProductBrandRepository.class).getById(id);
        Assert.isTrue(data.getMallId().equals(mallId), "不正确的品牌id");
        return new ProductBrand(data);
    }
    public static Page<ProductBrandDTO> page(Long current, Long size, Integer mallId) {
        return SpringUtil.getBean(ProductBrandRepository.class).page(1L, 999L, OperatorUtil.getMallId());
    }
    public static List<ProductBrand> instancesOf(Integer mallId) {
        return SpringUtil.getBean(ProductBrandRepository.class).listByMallId(mallId).stream().map(ProductBrand::new).collect(Collectors.toList());
    }
    public static List<ProductBrand> instancesOf(Set<Integer> ids) {
        return SpringUtil.getBean(ProductBrandRepository.class).listByIds(ids).stream().map(ProductBrand::new).collect(Collectors.toList());
    }
    public void save() {
        this.data = SpringUtil.getBean(ProductBrandRepository.class).save(this.data);
    }
    public void delete() {
        SpringUtil.getBean(ProductBrandRepository.class).delete(this.data);
        data = null;
    }
    public ProductBrandData getData() {
        return ObjectUtil.clone(this.data);
    }

    public SelectorImageDTO getSelector(){
        return new SelectorImageDTO(data.getName(), data.getId().toString(), data.getLogo());
    }

    public Integer getId() {
        return data.getId();
    }

    public String getName() {
        return data.getName();
    }
}
