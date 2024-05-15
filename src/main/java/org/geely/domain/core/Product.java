package org.geely.domain.core;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.apache.commons.lang3.StringUtils;
import org.geely.common.enums.EnableStateEnum;
import org.geely.common.enums.ProductStateEnum;
import org.geely.controller.dto.*;
import org.geely.controller.dto.site.ProductSiteDTO;
import org.geely.domain.common.Shop;
import org.geely.domain.common.data.MarketChannelRelData;
import org.geely.domain.core.data.*;
import org.geely.infrastructure.db.convert.CoreDomainDtoConvert;
import org.geely.infrastructure.db.repository.CommonDomainRepository;
import org.geely.infrastructure.db.repository.ProductRepository;
import org.geely.infrastructure.db.repository.SkuRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author cong huang
 */
public class Product {
    private ProductData data;
    private List<ProductTagData> tags;
    private List<ProductProps> props;
    private List<Sku> skus;
    private List<ProductImage> images;
    private List<MarketChannelRelData> marketChannels;

    private Product(ProductData data) {
        this.data = ObjectUtil.clone(data);
    }

    public static final String INVALID_MSG = "该商品已失效";

    public static Product newInstance(ProductCreateDTO dto) {
        dto.checkAndProcess();
        Product product = new Product(dto.toData());
        product.data.setId(null);
        product.data.setVersion(0);
        product.data.setCode(UUID.randomUUID().toString());
        product.data.setState(ProductStateEnum.ON);
        product.saveBaseData();
        product.newCode();
        product.saveVehicles(dto.getVehicleIds());
        product.saveTags(dto.getTagIds());
        product.saveProps(dto.getProps());
        product.saveImages(dto.getImages());
        product.skus = product.saveSkus(dto.getSkus());
        return product;
    }

    public static Product instanceOfShop(Integer productId, Integer shopId) {
        ProductData data = SpringUtil.getBean(ProductRepository.class).getById(productId);
        Assert.isTrue(data.getShopId().equals(shopId), "不正确的商品id");
        Product product = new Product(data);
        product.marketChannels = SpringUtil.getBean(CommonDomainRepository.class).listMarketChannelByShopId(product.data.getShopId());
        product.buildDetails(false);
        return product;
    }

    public static Product instanceOfSupplier(Integer productId, Integer supplierId) {
        ProductData data = SpringUtil.getBean(ProductRepository.class).getById(productId);
        Assert.isTrue(data.getSupplierId().equals(supplierId), "不正确的商品id");
        Product product = new Product(data);
        product.marketChannels = SpringUtil.getBean(CommonDomainRepository.class).listMarketChannelByShopId(product.data.getShopId());
        product.buildDetails(false);
        return product;
    }

    public static Product instanceOfCustomer(Integer skuId, Integer customerId) {
        SkuData skuData = SpringUtil.getBean(SkuRepository.class).getById(skuId);
        ProductData productData = SpringUtil.getBean(ProductRepository.class).getById(skuData.getProductId());
        //店铺
        Shop shop = Shop.instanceOfId(productData.getShopId());
        Assert.isTrue(shop.getState().equals(EnableStateEnum.ENABLE), INVALID_MSG);
        //供应商
        Supplier supplier = Supplier.instanceOf(shop.getSupplierId());
        Assert.isTrue(supplier.getState().equals(EnableStateEnum.ENABLE), INVALID_MSG);
        //渠道
        Integer channelId = Customer.instanceOf(customerId).getMarketChannelId();
        List<MarketChannelRelData> channels = SpringUtil.getBean(CommonDomainRepository.class).listMarketChannelByShopId(productData.getShopId()).stream().filter(x -> x.getMarketChannelId().equals(channelId)).collect(Collectors.toList());
        Assert.isFalse(channels.isEmpty(), INVALID_MSG);
        Product product = new Product(productData);
        product.marketChannels = channels;
        product.buildDetails(true);
        Assert.isTrue(product.skus.stream().anyMatch(x -> x.getId().equals(skuId)), INVALID_MSG);
        return product;
    }

    public static List<Product> instancesOf(Set<Integer> ids, Integer shopId) {
        List<ProductData> dataList = SpringUtil.getBean(ProductRepository.class).listByIds(ids);
        Assert.isTrue(dataList.stream().allMatch(x -> x.getShopId().equals(shopId)), "包含不正确的商品id");
        return dataList.stream().map(Product::new).collect(Collectors.toList());
    }


    private void buildDetails(boolean onlyEnable) {
        if (data.getQualityId() != null && !data.getQualityId().equals(0)) {
            data.setQuality(ProductQuality.instanceOf(data.getQualityId(), data.getMallId()).getName());
        }
        data.setCategory(ProductCategory.instanceOf(data.getCategoryId(), data.getMallId()).getName());
        data.setBrand(ProductBrand.instancesOf(data.getBrandId(), data.getMallId()).getName());
        tags = SpringUtil.getBean(ProductRepository.class).getTagsByProductId(data.getId());
        props = ProductProps.instancesOf(data.getId());
        images = ProductImage.instancesOf(data.getId());
        skus = Sku.instancesOf(data.getId(), onlyEnable);
    }

    public ProductShopDTO getShopDTO() {
        ProductDTO dto = CoreDomainDtoConvert.INSTANCE.convert(data);
        return new ProductShopDTO(dto, tags, images, props, skus, marketChannels);
    }

    public ProductSiteDTO getSiteDTO() {
        ProductDTO dto = CoreDomainDtoConvert.INSTANCE.convert(data);
        Shop shop = Shop.instanceOfId(data.getShopId());
        ShopSimpleDTO shopDto = new ShopSimpleDTO();
        shopDto.setId(data.getShopId());
        shopDto.setName(shop.getName());
        return new ProductSiteDTO(dto, tags, images, props, skus, marketChannels.get(0), shopDto);
    }

    public ProductSupplierDTO getSupplierDTO() {
        ProductDTO dto = CoreDomainDtoConvert.INSTANCE.convert(data);
        Shop shop = Shop.instanceOfId(data.getShopId());
        ShopSimpleDTO shopDto = new ShopSimpleDTO();
        shopDto.setId(data.getShopId());
        shopDto.setName(shop.getName());
        return new ProductSupplierDTO(dto, tags, images, props, skus, marketChannels, shopDto);
    }

    public void newCode() {
        data.setCode("P" +
                StringUtils.leftPad(data.getShopId().toString(), 2, '0') +
                StringUtils.leftPad(data.getId().toString(), 4, '0'));
        save();
    }

    public ProductData getData() {
        return ObjectUtil.clone(this.data);
    }

    public void update(ProductUpdateDTO dto) {
        dto.checkAndProcess();
        boolean changed = updateVehicles(dto.getVehicleIds());
        if (updateTags(dto.getTagIds())) {
            changed = true;
        }
        if (updateProps(dto.getProps())) {
            changed = true;
        }
        if (updateImages(dto.getImages())) {
            changed = true;
        }
        if (updateSkus(dto.getSkus())) {
            changed = true;
        }
        updateBaseData(dto, changed);
    }

    private boolean updateImages(List<String> imageList) {
        if (imageList == null) {
            imageList = new ArrayList<>();
        }
        boolean changed = false;
        //保持页面上的图片顺序
        AtomicInteger sort = new AtomicInteger(1);
        Map<String, Integer> imageSortMap = new HashMap<>();
        imageList.forEach(x -> imageSortMap.put(x, sort.getAndIncrement()));

        //删除旧图片或更新旧图片位置
        List<ProductImage> updateModels = new ArrayList<>();
        Set<String> oldImages = new HashSet<>();
        for (ProductImage image : images) {
            String imageUrl = image.getUrl();
            oldImages.add(imageUrl);
            if (imageSortMap.containsKey(imageUrl)) {
                if (image.updateSort(imageSortMap.get(imageUrl))) {
                    changed = true;
                }
                updateModels.add(image);
            } else {
                image.delete();
                changed = true;
            }
        }
        //插入新图片
        List<ProductImageData> newList = imageSortMap.keySet().stream().filter(x -> !oldImages.contains(x)).map(x -> new ProductImageData(this.data.getId(), x, imageSortMap.get(x))).collect(Collectors.toList());
        if (!newList.isEmpty()) {
            List<ProductImage> newModels = ProductImage.newInstances(newList);
            updateModels.addAll(newModels);
            changed = true;
        }
        //按顺序返回
        this.images = updateModels.stream().sorted(Comparator.comparing(ProductImage::getSort)).collect(Collectors.toList());
        return changed;
    }

    private boolean updateProps(List<ProductPropsUpdateDTO> propsList) {
        if (propsList == null) {
            propsList = new ArrayList<>();
        }
        boolean changed = false;
        //保持页面上的顺序
        AtomicInteger sort = new AtomicInteger(1);
        List<ProductPropsData> dataList = propsList.stream().map(x -> new ProductPropsData(x, this.data.getId(), sort.getAndIncrement())).collect(Collectors.toList());
        Map<Integer, ProductPropsData> updateMap = dataList.stream().filter(x -> x.getId() != 0).collect(Collectors.toMap(ProductPropsData::getId, v -> v));
        Set<Integer> oldIds = new HashSet<>();
        //删除或更新
        List<ProductProps> updateModels = new ArrayList<>();
        for (ProductProps prop : this.props) {
            Integer oldId = prop.getId();
            oldIds.add(oldId);
            if (updateMap.containsKey(oldId)) {
                if (prop.update(updateMap.get(oldId))) {
                    changed = true;
                }
                updateModels.add(prop);
            } else {
                prop.delete();
                changed = true;
            }
        }
        //更新id必须都存在
        Assert.isTrue(updateMap.keySet().isEmpty() || oldIds.containsAll(updateMap.keySet()), "未知的商品属性id");
        //插入新属性
        List<ProductPropsData> newList = dataList.stream().filter(x -> x.getId() == 0).collect(Collectors.toList());
        if (!newList.isEmpty()) {
            List<ProductProps> newModels = ProductProps.newInstances(newList);
            updateModels.addAll(newModels);
            changed = true;
        }

        //按顺序返回
        this.props = updateModels.stream().sorted(Comparator.comparing(ProductProps::getSort)).collect(Collectors.toList());
        return changed;
    }

    private boolean updateTags(Set<Integer> tagIds) {
        if (tagIds == null) {
            tagIds = new HashSet<>();
        }
        boolean changed = false;
        Set<Integer> oldTagIds = new HashSet<>();
        Set<Integer> deleteIds = new HashSet<>();
        List<ProductTagData> tagDataList = new ArrayList<>();
        for (ProductTagData tag : this.tags) {
            oldTagIds.add(tag.getId());
            if (tagIds.contains(tag.getId())) {
                tagDataList.add(tag);
            } else {
                deleteIds.add(tag.getId());
            }
        }
        ProductRepository repository = SpringUtil.getBean(ProductRepository.class);
        //删除标签
        if (repository.removeTags(this.data.getId(), deleteIds)) {
            changed = true;
        }
        //插入新标签
        Set<Integer> newTagIds = tagIds.stream().filter(x -> !oldTagIds.contains(x)).collect(Collectors.toSet());
        if (!newTagIds.isEmpty()) {
            List<ProductTagData> newTagDataList = ProductTag.instancesOf(newTagIds, this.data.getMallId()).stream().map(ProductTag::getData).collect(Collectors.toList());
            List<ProductTagRelData> relDataList = newTagDataList.stream().map(x -> new ProductTagRelData(x.getId(), data.getId())).collect(Collectors.toList());
            repository.saveTags(relDataList);
            tagDataList.addAll(newTagDataList);
            changed = true;
        }
        this.tags = tagDataList.stream().sorted(Comparator.comparing(ProductTagData::getId)).collect(Collectors.toList());
        return changed;
    }

    private boolean updateVehicles(Set<Integer> vehicleIds) {
        return false;
    }

    private void saveBaseData() {
        //验证并赋值
        String category = ProductCategory.instanceOf(data.getCategoryId(), data.getMallId()).getName();
        String brand = ProductBrand.instancesOf(data.getBrandId(), data.getMallId()).getName();
        String quality = null;
        if (data.getQualityId() == null) {
            data.setQualityId(0);
        }
        if (!data.getQualityId().equals(0)) {
            quality = ProductQuality.instanceOf(data.getQualityId(), data.getMallId()).getName();
        }
        save();
        data.setCategory(category);
        data.setBrand(brand);
        data.setQuality(quality);
    }

    public void save() {
        this.data = SpringUtil.getBean(ProductRepository.class).save(this.data);
    }

    public Product setState(ProductStateEnum state) {
        this.data.setState(state);
        return this;
    }

    private void updateBaseData(ProductUpdateDTO dto, boolean changed) {
        String category = data.getCategory();
        String brand = data.getBrand();
        String quality = data.getQuality();
        if (!data.getCategoryId().equals(dto.getCategoryId())) {
            changed = true;
            data.setCategoryId(dto.getCategoryId());
            category = ProductCategory.instanceOf(data.getCategoryId(), data.getMallId()).getName();
        }
        if (dto.getQualityId() == null) {
            dto.setQualityId(0);
        }
        if (!data.getQualityId().equals(dto.getQualityId())) {
            changed = true;
            data.setQualityId(dto.getQualityId());
            if (data.getQualityId().equals(0)) {
                quality = "";
            } else {
                quality = ProductQuality.instanceOf(data.getQualityId(), data.getMallId()).getName();
            }
        }
        if (!data.getName().equals(dto.getName())) {
            changed = true;
            data.setName(dto.getName());
        }
        if (!data.getDescription().equals(dto.getDescription())) {
            changed = true;
            data.setDescription(dto.getDescription());
        }
        if (changed) {
            this.data = SpringUtil.getBean(ProductRepository.class).save(this.data);
            data.setCategory(category);
            data.setBrand(brand);
            data.setQuality(quality);
        }
    }

    private void saveImages(List<String> images) {
        if (images == null || images.isEmpty()) {
            this.images = new ArrayList<>();
            return;
        }
        AtomicInteger sort = new AtomicInteger(1);
        List<ProductImageData> dataList = images.stream().map(x -> new ProductImageData(data.getId(), x, sort.getAndIncrement())).collect(Collectors.toList());
        this.images = ProductImage.newInstances(dataList);
    }

    private List<Sku> saveSkus(List<SkuCreateDTO> skuCreates) {
        Assert.isFalse(skuCreates == null || skuCreates.isEmpty(), "新增的sku不能为空");
        //渠道
        if (this.marketChannels == null) {
            this.marketChannels = SpringUtil.getBean(CommonDomainRepository.class).listMarketChannelByShopId(this.data.getShopId());
        }
        Set<Integer> channelIds = this.marketChannels.stream().map(MarketChannelRelData::getMarketChannelId).collect(Collectors.toSet());
        //物料
        Set<Integer> materialIds = skuCreates.stream().map(SkuCreateDTO::getMaterialId).collect(Collectors.toSet());
        Map<Integer, MaterialData> materialMap = Material.instancesOf(materialIds, data.getShopId()).stream().map(Material::getData).collect(Collectors.toMap(MaterialData::getId, x -> x));
        //规格
        Set<Integer> specsTypeIds = new HashSet<>();
        for (SkuCreateDTO dto : skuCreates) {
            dto.checkChannels(channelIds);
            specsTypeIds.addAll(dto.getSpecs().stream().map(SkuSpecsRelCreateDTO::getSpecsTypeId).collect(Collectors.toList()));
        }
        Map<Integer, String> specsTypeMap = SkuSpecsType.instancesOf(specsTypeIds, data.getMallId()).stream().map(SkuSpecsType::getData).collect(Collectors.toMap(SkuSpecsTypeData::getId, SkuSpecsTypeData::getName));
        return skuCreates.stream().map(x -> Sku.newInstance(this.data.getId(), x, materialMap.get(x.getMaterialId()), specsTypeMap)).sorted(Comparator.comparing(Sku::getSort)).collect(Collectors.toList());
    }

    private boolean updateSkus(List<SkuUpdateDTO> skuUpdateDTOS) {
        Assert.isFalse(skuUpdateDTOS == null || skuUpdateDTOS.isEmpty(), "sku不能为空");
        boolean changed = false;
        //更新或删除sku
        Map<Integer, SkuUpdateDTO> updateSkuSet = skuUpdateDTOS.stream().filter(x -> x.getId() != 0).collect(Collectors.toMap(SkuUpdateDTO::getId, v -> v));
        //物料相关
        Map<Integer, MaterialData> materialMap = new HashMap<>();
        for (Sku sku : this.skus) {
            if (!materialMap.containsKey(sku.getMaterialId())) {
                materialMap.put(sku.getMaterialId(), sku.getMaterialData());
            }
        }
        Set<Integer> newMaterialIds = updateSkuSet.values().stream().map(SkuCreateDTO::getMaterialId).filter(x -> !materialMap.containsKey(x)).collect(Collectors.toSet());
        Material.instancesOf(newMaterialIds, data.getShopId()).forEach(x -> {
            MaterialData m = x.getData();
            materialMap.put(m.getId(), m);
        });
        List<Sku> updateModels = new ArrayList<>();
        Set<Integer> channelIds = this.marketChannels.stream().map(MarketChannelRelData::getMarketChannelId).collect(Collectors.toSet());
        for (Sku sku : this.skus) {
            Integer skuId = sku.getId();
            if (updateSkuSet.containsKey(skuId)) {
                SkuUpdateDTO updateDto = updateSkuSet.get(skuId);
                updateDto.checkChannels(channelIds);
                if (sku.update(updateDto, materialMap.get(updateDto.getMaterialId()))) {
                    changed = true;
                }
                updateModels.add(sku);
            } else {
                sku.delete();
                changed = true;
            }
        }
        //插入新sku
        List<SkuCreateDTO> newSkus = skuUpdateDTOS.stream().filter(x -> x.getId() == 0).collect(Collectors.toList());
        if (!newSkus.isEmpty()) {
            changed = true;
            updateModels.addAll(saveSkus(newSkus));
        }
        this.skus = updateModels.stream().sorted(Comparator.comparing(Sku::getSort)).collect(Collectors.toList());
        return changed;
    }

    private void saveProps(List<ProductPropsCreateDTO> props) {
        if (props == null || props.isEmpty()) {
            return;
        }
        AtomicInteger sort = new AtomicInteger(1);
        List<ProductPropsData> propsDataList = props.stream().map(x -> new ProductPropsData(data.getId(), x.getName(), x.getValue(), sort.getAndIncrement())).collect(Collectors.toList());
        this.props = ProductProps.newInstances(propsDataList);
    }

    private void saveVehicles(Set<Integer> vehicleIds) {
        if (vehicleIds == null || vehicleIds.isEmpty()) {
            return;
        }
        vehicleIds.forEach(x -> {

        });
    }

    private void saveTags(Set<Integer> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) {
            return;
        }
        tags = ProductTag.instancesOf(tagIds, data.getMallId()).stream().map(ProductTag::getData).collect(Collectors.toList());
        List<ProductTagRelData> relDataList = tags.stream().map(x -> new ProductTagRelData(x.getId(), data.getId())).collect(Collectors.toList());
        SpringUtil.getBean(ProductRepository.class).saveTags(relDataList);
    }

    public ProductStateEnum getState() {
        return data.getState();
    }
}
