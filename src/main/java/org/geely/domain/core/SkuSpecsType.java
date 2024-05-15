package org.geely.domain.core;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.geely.controller.dto.SelectorDTO;
import org.geely.domain.core.data.SkuSpecsTypeData;
import org.geely.infrastructure.db.repository.SkuSpecsTypeRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author cong huang
 */
public class SkuSpecsType {
    private SkuSpecsTypeData data;

    private SkuSpecsType(SkuSpecsTypeData data) {
        this.data = ObjectUtil.clone(data);
    }

    public static SkuSpecsType newInstance(SkuSpecsTypeData data) {
        SkuSpecsType instance = new SkuSpecsType(data);
        instance.data.setId(null);
        instance.data.setVersion(0);
        instance.save();
        return instance;
    }

    public static SkuSpecsType InstanceOf(Integer id, Integer mallId) {
        SkuSpecsTypeData data = SpringUtil.getBean(SkuSpecsTypeRepository.class).getById(id);
        Assert.notNull(data, "规格类型不存在");
        Assert.isTrue(data.getMallId().equals(mallId), "不正确的规格类型");
        return new SkuSpecsType(data);
    }

    public static List<SkuSpecsType> instancesOf(Set<Integer> specsIds, Integer mallId) {
        List<SkuSpecsTypeData> dataList = SpringUtil.getBean(SkuSpecsTypeRepository.class).listByIds(specsIds);
        Assert.isTrue(dataList.stream().allMatch(x->x.getMallId().equals(mallId)), "包含不正确的规格类型id");
        return dataList.stream().map(SkuSpecsType::new).collect(Collectors.toList());
    }

    public static List<SkuSpecsType> instancesOf(Integer mallId) {
        return SpringUtil.getBean(SkuSpecsTypeRepository.class).listByMallId(mallId).stream().map(SkuSpecsType::new).collect(Collectors.toList());
    }

    public void save() {
        this.data = SpringUtil.getBean(SkuSpecsTypeRepository.class).save(this.data);
    }

    public SkuSpecsTypeData getData() {
        return ObjectUtil.clone(this.data);
    }

    public SkuSpecs getSpecsByName(String name) {
        return SkuSpecs.instanceOf(data.getId(), name);
    }

    public Integer getId() {
        return data.getId();
    }
    public SelectorDTO getSelector() {
        return new SelectorDTO(data.getName(), data.getId().toString());
    }
}
