package org.geely.infrastructure.db.repository;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.geely.controller.dto.ProductCategoryDTO;
import org.geely.domain.core.data.ProductCategoryData;
import org.geely.infrastructure.db.ProductCategoryDO;
import org.geely.infrastructure.db.convert.SupportDomainDataConvert;
import org.geely.infrastructure.db.convert.SupportDomainDtoConvert;
import org.geely.infrastructure.db.service.ProductCategoryDbService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author cong huang
 */
@Repository
public class ProductCategoryRepository {
    @Resource
    private ProductCategoryDbService service;
    public static final String CATEGORY_NOT_EXIST = "商品目录不存在";

    public ProductCategoryData save(ProductCategoryData data) {
        Assert.isFalse(exist(data), "商品目录已存在");
        if (data.getId() == null || data.getId().equals(0)) {
            if (data.getParentId() == null) {
                data.setParentId(0);
            }
            if (!data.getParentId().equals(0)) {
                //新增时验证父节点
                ProductCategoryDO parentNode = service.getById(data.getParentId());
                Assert.notNull(parentNode, "父节点不存在");
                Assert.isTrue(parentNode.getMallId().equals(data.getMallId()), "父节点租户id不一致");
                data.setLevel(parentNode.getLevel() + 1);
            } else {
                data.setLevel(1);
            }
            data.setSort(newSort(data.getParentId()));
        }
        ProductCategoryDO categoryDO = SupportDomainDataConvert.INSTANCE.convert(data);
        boolean result = service.saveOrUpdate(categoryDO);
        Assert.isTrue(result, "商品目录保存失败");
        return SupportDomainDataConvert.INSTANCE.convert(categoryDO);
    }

    public void delete(List<ProductCategoryData> list) {
        Assert.notNull(list, CATEGORY_NOT_EXIST);
        Assert.isTrue(!list.isEmpty(), CATEGORY_NOT_EXIST);
        boolean result = service.removeBatchByIds(list.stream().map(ProductCategoryData::getId).collect(Collectors.toList()));
        Assert.isTrue(result, "商品目录删除失败");
    }

    public boolean updateBatchById(List<ProductCategoryData> list) {
        return service.updateBatchById(list.stream().map(SupportDomainDataConvert.INSTANCE::convert).collect(Collectors.toList()));
    }

    public ProductCategoryData getById(Integer id) {
        ProductCategoryDO result = service.getById(id);
        Assert.notNull(result, CATEGORY_NOT_EXIST);
        return SupportDomainDataConvert.INSTANCE.convert(result);
    }

    public List<ProductCategoryDTO> listTrees(Integer mallId) {
        Map<Integer, List<ProductCategoryDO>> map = mapByParentId(mallId);
        List<ProductCategoryDTO> result = new ArrayList<>();
        if (map == null || map.isEmpty()) {
            return result;
        }
        List<ProductCategoryDO> parents = map.getOrDefault(0, null);
        if (parents != null && !parents.isEmpty()) {
            result.addAll(parents.stream().sorted(Comparator.comparing(ProductCategoryDO::getSort)).map(SupportDomainDtoConvert.INSTANCE::convert).collect(Collectors.toList()));
        }
        if (result.isEmpty()) {
            return result;
        }
        buildTree(result, map, 1);
        return result;
    }

    private void buildTree(List<ProductCategoryDTO> result, Map<Integer, List<ProductCategoryDO>> map, int level) {
        if (level > 5) {
            return;
        }
        for (ProductCategoryDTO dto : result) {
            List<ProductCategoryDO> parents = map.getOrDefault(dto.getId(), null);
            if (parents != null && !parents.isEmpty()) {
                dto.setChildren(new ArrayList<>());
                List<ProductCategoryDTO> children = parents.stream().sorted(Comparator.comparing(ProductCategoryDO::getSort)).map(SupportDomainDtoConvert.INSTANCE::convert).collect(Collectors.toList());
                buildTree(children, map, level + 1);
                dto.getChildren().addAll(children);
            }
        }
    }

    private Map<Integer, List<ProductCategoryDO>> mapByParentId(Integer mallId) {
        LambdaQueryWrapper<ProductCategoryDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductCategoryDO::getMallId, mallId);
        return service.list(wrapper).stream().collect(Collectors.groupingBy(ProductCategoryDO::getParentId));
    }

    public ProductCategoryData getNeighbor(ProductCategoryData data, boolean isPrevious) {
        LambdaQueryWrapper<ProductCategoryDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductCategoryDO::getParentId, data.getParentId());
        if (isPrevious) {
            wrapper.lt(ProductCategoryDO::getSort, data.getSort()).orderByDesc(ProductCategoryDO::getSort);
        } else {
            wrapper.gt(ProductCategoryDO::getSort, data.getSort()).orderByAsc(ProductCategoryDO::getSort);
        }
        wrapper.last("limit 1");
        List<ProductCategoryDO> list = service.list(wrapper);
        if (list.isEmpty()) {
            return null;
        }
        return SupportDomainDataConvert.INSTANCE.convert(list.get(0));
    }

    private Integer newSort(Integer parentId) {
        LambdaQueryWrapper<ProductCategoryDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductCategoryDO::getParentId, parentId).orderByDesc(ProductCategoryDO::getSort).last("limit 1");
        List<ProductCategoryDO> list = service.list(wrapper);
        if (list.isEmpty()) {
            return 1;
        } else {
            return list.get(0).getSort() + 1;
        }
    }

    private boolean exist(ProductCategoryData data) {
        LambdaQueryWrapper<ProductCategoryDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductCategoryDO::getMallId, data.getMallId())
                .eq(ProductCategoryDO::getParentId, data.getParentId())
                .eq(ProductCategoryDO::getName, data.getName());
        if (data.getId() != null && !data.getId().equals(0)) {
            wrapper.not(x -> x.eq(ProductCategoryDO::getId, data.getId()));
        }
        long count = service.count(wrapper);
        return count > 0;
    }

    public List<ProductCategoryData> getByIds(Set<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }
        return service.listByIds(ids).stream().map(SupportDomainDataConvert.INSTANCE::convert).collect(Collectors.toList());
    }
}
