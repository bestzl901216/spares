package org.geely.domain.core;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.geely.controller.dto.SelectorDTO;
import org.geely.domain.core.data.SkuSpecsData;
import org.geely.infrastructure.db.repository.SkuSpecsRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author cong huang
 */
public class SkuSpecs {
    private SkuSpecsData data;

    private SkuSpecs(SkuSpecsData data) {
        this.data = ObjectUtil.clone(data);
    }

    public static SkuSpecs newInstance(SkuSpecsData data) {
        SkuSpecs specs = new SkuSpecs(data);
        specs.data.setId(null);
        specs.data.setVersion(0);
        specs.save();
        return specs;
    }

    public static SkuSpecs instanceOf(Integer typeId, String name) {
        SkuSpecsData data = SpringUtil.getBean(SkuSpecsRepository.class).getByName(typeId, name);
        return new SkuSpecs(data);
    }

    public static List<SkuSpecs> instancesOf(Set<Integer> specsIds) {
        Assert.notNull(specsIds);
        Assert.isFalse(specsIds.isEmpty(), "规格不能为空");
        List<SkuSpecsData> dataList = SpringUtil.getBean(SkuSpecsRepository.class).listByIds(specsIds);
        Assert.isTrue(specsIds.size() == dataList.size(), "包含不存在的规格");
        return  dataList.stream().map(SkuSpecs::new).collect(Collectors.toList());
    }

    public static List<SkuSpecs> instancesOf(Integer typeId, Integer mallId) {
        SkuSpecsType.InstanceOf(typeId, mallId);
        return SpringUtil.getBean(SkuSpecsRepository.class).listByTypeId(typeId).stream().map(SkuSpecs::new).collect(Collectors.toList());
    }

    public void save() {
        this.data = SpringUtil.getBean(SkuSpecsRepository.class).save(this.data);
    }

    public SkuSpecsData getData() {
        return ObjectUtil.clone(this.data);
    }

    public void setSelectable() {
        data.setSelectable(true);
    }

    public Integer getId() {
        return data.getId();
    }
    public SelectorDTO getSelector() {
        return  new SelectorDTO(data.getName(), data.getId().toString());
    }
}
