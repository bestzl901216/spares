package org.geely.domain.core;


import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.mysql.cj.util.StringUtils;
import org.geely.controller.dto.ProductCategoryDTO;
import org.geely.domain.core.data.ProductCategoryData;
import org.geely.infrastructure.db.repository.ProductCategoryRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 租户
 *
 * @author cong huang
 */
public class ProductCategory {
    private ProductCategoryData data;

    private ProductCategory(ProductCategoryData data) {
        this.data = ObjectUtil.clone(data);
    }

    public static ProductCategory newInstance(ProductCategoryData data) {
        ProductCategory category = new ProductCategory(data);
        category.data.setId(null);
        category.data.setVersion(0);
        category.save();
        return category;
    }

    public static ProductCategory instanceOf(Integer id, Integer mallId) {
        ProductCategoryData data = SpringUtil.getBean(ProductCategoryRepository.class).getById(id);
        Assert.isTrue(mallId.compareTo(data.getMallId()) == 0, "租户id不正确");
        return new ProductCategory(data);
    }

    public static List<ProductCategoryDTO> filterByIdAndParents(List<ProductCategoryDTO> result, Set<Integer> categoryIds) {
        List<ProductCategory> categories = ProductCategory.instancesOf(categoryIds);
        Set<Integer> allIds = new HashSet<>();
        for (ProductCategory category : categories) {
            ProductCategoryData data = category.getData();
            allIds.add(data.getId());
            if (!StringUtils.isNullOrEmpty(data.getParentPath())) {
                allIds.addAll(Arrays.stream(data.getParentPath().split(",")).map(Integer::parseInt).collect(Collectors.toList()));
            }
        }
        return filter(result, allIds);
    }

    private static List<ProductCategoryDTO> filter(List<ProductCategoryDTO> result, Set<Integer> allIds) {
        if (result == null || result.isEmpty()) {
            return result;
        }
        result = result.stream().filter(x -> allIds.contains(x.getId())).collect(Collectors.toList());
        for (ProductCategoryDTO productCategoryDTO : result) {
            productCategoryDTO.setChildren(filter(productCategoryDTO.getChildren(), allIds));
        }
        return result;
    }

    private static List<ProductCategory> instancesOf(Set<Integer> categoryIds) {
        return SpringUtil.getBean(ProductCategoryRepository.class).getByIds(categoryIds).stream().map(ProductCategory::new).collect(Collectors.toList());
    }

    public boolean update(String name) {
        Assert.notNull(name, "名称不能为空");
        Assert.notBlank(name, "名称不能为空");
        if (name.equals(this.data.getName())) {
            return false;
        }
        this.data.setName(name);
        save();
        return true;
    }

    public static List<ProductCategoryDTO> listTrees(Integer mallId) {
        return SpringUtil.getBean(ProductCategoryRepository.class).listTrees(mallId);
    }

    public static Set<Integer> getTreeIds(Integer mallId, Integer id) {
        List<ProductCategoryDTO> trees = listTrees(mallId);
        if (trees.isEmpty()) {
            return new HashSet<>();
        }
        ProductCategoryDTO node = findById(trees, id);
        if (node == null) {
            return new HashSet<>();
        }
        Set<Integer> treeIds = new HashSet<>();
        buildTreeIds(treeIds, node);
        return treeIds;
    }

    private static void buildTreeIds(Set<Integer> treeIds, ProductCategoryDTO node) {
        treeIds.add(node.getId());
        if (node.getChildren() == null) {
            return;
        }
        for (ProductCategoryDTO child : node.getChildren()) {
            buildTreeIds(treeIds, child);
        }
    }


    public static Set<Integer> getLeafIds(Integer mallId, Integer id) {
        List<ProductCategoryDTO> trees = listTrees(mallId);
        if (trees.isEmpty()) {
            return new HashSet<>();
        }
        ProductCategoryDTO node = findById(trees, id);
        if (node == null) {
            return new HashSet<>();
        }
        Set<Integer> leafIds = new HashSet<>();
        buildLeafIds(leafIds, node);
        return leafIds;
    }

    private static void buildLeafIds(Set<Integer> leafIds, ProductCategoryDTO node) {
        if (node == null) {
            return;
        }
        if (node.getChildren() == null || node.getChildren().isEmpty()) {
            leafIds.add(node.getId());
        } else {
            for (ProductCategoryDTO child : node.getChildren()) {
                buildLeafIds(leafIds, child);
            }
        }
    }

    public static ProductCategoryDTO findById(List<ProductCategoryDTO> trees, Integer id) {
        Assert.notEmpty(trees, "商品分类树不能为空");
        for (ProductCategoryDTO tree : trees) {
            if (tree.getId().equals(id)) {
                return tree;
            } else {
                if (tree.getChildren() != null && !tree.getChildren().isEmpty()) {
                    ProductCategoryDTO node = findById(tree.getChildren(), id);
                    if (node != null) {
                        return node;
                    }
                }
            }
        }
        return null;
    }

    private void save() {
        this.data = SpringUtil.getBean(ProductCategoryRepository.class).save(this.data);
    }

    public boolean moveUp() {
        return move(true);
    }

    public boolean moveDown() {
        return move(false);
    }

    private boolean move(boolean isUp) {
        ProductCategoryRepository repository = SpringUtil.getBean(ProductCategoryRepository.class);
        ProductCategoryData target = repository.getNeighbor(this.data, isUp);
        if (target == null) {
            return false;
        }
        int sort = this.data.getSort();
        this.data.setSort(target.getSort());
        target.setSort(sort);
        List<ProductCategoryData> updateList = new ArrayList<>();
        updateList.add(target);
        updateList.add(this.data);
        return repository.updateBatchById(updateList);
    }

    public void delete() {
        data = null;
    }

    public ProductCategoryData getData() {
        return ObjectUtil.clone(this.data);
    }

    public Integer getId() {
        return data.getId();
    }

    public String getCode() {
        if (StringUtils.isNullOrEmpty(data.getParentPath())) {
            return data.getId().toString();
        } else {
            return data.getParentPath() + "," + data.getId();
        }
    }

    public String getName() {
        return data.getName();
    }
}
