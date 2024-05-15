package org.geely.domain.core;


import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.geely.common.enums.EnableStateEnum;
import org.geely.controller.dto.ProductTagPageDTO;
import org.geely.controller.dto.SelectorDTO;
import org.geely.domain.core.data.ProductTagData;
import org.geely.infrastructure.db.repository.ProductTagRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 租户
 *
 * @author cong huang
 */
public class ProductTag {
    private ProductTagData data;

    private ProductTag(ProductTagData productTagData) {
        this.data = ObjectUtil.clone(productTagData);
    }

    public static ProductTag newInstance(ProductTagData data) {
        ProductTag tag = new ProductTag(data);
        tag.data.setId(null);
        tag.data.setSort(0);
        tag.data.setState(EnableStateEnum.ENABLE);
        tag.data.setVersion(0);
        tag.save();
        return tag;
    }

    public static ProductTag instanceOf(Integer id, Integer mallId) {
        ProductTagData data = SpringUtil.getBean(ProductTagRepository.class).getById(id);
        Assert.isTrue(mallId.equals(data.getMallId()), "不正确的标签id");
        return new ProductTag(data);
    }

    public static List<ProductTag> instancesOf(Set<Integer> ids, Integer mallId) {
        List<ProductTagData> list = SpringUtil.getBean(ProductTagRepository.class).listByIds(ids);
        Assert.isTrue(list.size() == ids.size(), "包含不存在的标签id");
        Assert.isTrue(list.stream().allMatch(x->x.getMallId().equals(mallId)), "包含不正确的标签id");
        return list.stream().map(ProductTag::new).collect(Collectors.toList());
    }

    public static List<ProductTag> instancesOf(Integer mallId) {
        return SpringUtil.getBean(ProductTagRepository.class).listByMallId(mallId).stream().map(ProductTag::new).collect(Collectors.toList());
    }
    public static List<ProductTag> instancesOf(Set<Integer> ids) {
        return SpringUtil.getBean(ProductTagRepository.class).listByIds(ids).stream().map(ProductTag::new).collect(Collectors.toList());
    }

    public static Page<ProductTagPageDTO> page(Long current, Long size, Integer mallId) {
        return SpringUtil.getBean(ProductTagRepository.class).page(current, size, mallId);
    }

    public void save() {
        this.data = SpringUtil.getBean(ProductTagRepository.class).save(this.data);
    }

    public void delete() {
        SpringUtil.getBean(ProductTagRepository.class).delete(this.data);
        data = null;
    }

    public ProductTagData getData() {
        return ObjectUtil.clone(this.data);
    }
    public Integer getId(){
        return data.getId();
    }

    public SelectorDTO getSelector() {
        return new SelectorDTO(data.getName(), data.getId().toString());
    }
}
