package org.geely.domain.core;


import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.geely.domain.core.data.MaterialData;
import org.geely.infrastructure.db.repository.MaterialRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 租户
 *
 * @author cong huang
 */
public class Material {
    private MaterialData data;

    private Material(MaterialData data) {
        this.data = ObjectUtil.clone(data);
    }
    public static Material instanceOf(Integer id, Integer supplierId, Integer shopId) {
        MaterialData data = SpringUtil.getBean(MaterialRepository.class).getById(id);
        Assert.isTrue(supplierId.equals(data.getSupplierId()), "不正确的物料id");
        Assert.isTrue(SpringUtil.getBean(MaterialRepository.class).existRel(id, shopId),"不正确的物料id");
        return new Material(data);
    }
    public static List<Material> instancesOf(Set<Integer> ids, Integer shopId) {
        if(ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }
        MaterialRepository repository = SpringUtil.getBean(MaterialRepository.class);
        Assert.isTrue(repository.allExistRel(ids, shopId), "包含不正确的物料id");
        return repository.listByIds(ids).stream().map(Material::new).collect(Collectors.toList());
    }
    public static List<Material> instancesOf(Set<Integer> ids) {
        if(ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }
        return SpringUtil.getBean(MaterialRepository.class).listByIds(ids).stream().map(Material::new).collect(Collectors.toList());
    }
    public MaterialData getData() {
        return ObjectUtil.clone(this.data);
    }
}
