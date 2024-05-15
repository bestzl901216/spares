package org.geely.domain.core;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.apache.commons.lang3.StringUtils;
import org.geely.common.enums.EnableStateEnum;
import org.geely.controller.dto.*;
import org.geely.controller.dto.site.SkuSiteDTO;
import org.geely.domain.common.data.MarketChannelRelData;
import org.geely.domain.common.utils.PriceUtil;
import org.geely.domain.core.data.*;
import org.geely.infrastructure.db.convert.CoreDomainDtoConvert;
import org.geely.infrastructure.db.convert.SiteDtoConvert;
import org.geely.infrastructure.db.repository.SkuRepository;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Sku {
    private SkuData data;
    private MaterialData material;
    private List<SkuImage> images;
    private List<SkuSpecsData> specs;
    private List<SkuPrice> prices;

    private Sku(SkuData data) {
        this.data = ObjectUtil.clone(data);
    }

    public static Sku instanceOf(Integer id) {
        return new Sku(SpringUtil.getBean(SkuRepository.class).getById(id));
    }

    public static List<Sku> instancesOf(Set<Integer> skuIds) {
        return SpringUtil.getBean(SkuRepository.class).getByIds(skuIds).stream().map(Sku::new).collect(Collectors.toList());
    }

    public static void releaseStock(Map<Integer, Integer> skuQtyMap) {
        if (skuQtyMap == null || skuQtyMap.isEmpty()) {
            return;
        }
        List<Sku> skus = Sku.instancesOf(skuQtyMap.keySet());
        for (Sku sku : skus) {
            sku.releaseStock(skuQtyMap.get(sku.getId()));
        }
    }

    public SkuData getData() {
        return ObjectUtil.clone(data);
    }

    public MaterialData getMaterialData() {
        return ObjectUtil.clone(material);
    }

    public static Sku newInstance(Integer productId, SkuCreateDTO dto, MaterialData material, Map<Integer, String> specsTypeMap) {
        SkuData data = dto.toData();
        Sku sku = new Sku(data);
        sku.data.setId(null);
        sku.data.setVersion(0);
        sku.data.setSalesVolume(0);
        sku.data.setProductId(productId);
        sku.data.setCode(UUID.randomUUID().toString());
        sku.material = material;
        List<String> images = dto.getImages();
        sku.data.setDefaultImage(images == null || images.isEmpty() ? "" : images.get(0));
        sku.save();
        sku.newCode();
        sku.saveImages(images);
        sku.saveSpecs(dto.getSpecs(), specsTypeMap);
        sku.saveChannelPrices(dto.getChannelPrices());
        return sku;
    }

    private void newCode() {
        data.setCode("K" + StringUtils.leftPad(data.getId().toString(), 6, '0'));
        save();
    }

    public static List<Sku> instancesOf(Integer productId, boolean onlyEnable) {
        SkuRepository repository = SpringUtil.getBean(SkuRepository.class);
        List<SkuData> skuDataList = repository.listByProductId(productId, onlyEnable);
        Set<Integer> skuIds = skuDataList.stream().map(SkuData::getId).collect(Collectors.toSet());
        //物料
        Map<Integer, MaterialData> materialMap = Material.instancesOf(skuDataList.stream().map(SkuData::getMaterialId).collect(Collectors.toSet())).stream().map(Material::getData).collect(Collectors.toMap(MaterialData::getId, v -> v));
        //图片
        Map<Integer, List<SkuImage>> imagesMap = SkuImage.instancesOf(skuIds);
        //规格
        Map<Integer, List<SkuSpecsData>> specsMap = repository.listSpecsBySkuIds(skuIds);
        //渠道价格
        Map<Integer, List<SkuPrice>> priceMap = SkuPrice.instancesOf(skuIds).stream().collect(Collectors.groupingBy(SkuPrice::getSkuId));

        List<Sku> skus = skuDataList.stream().map(Sku::new).collect(Collectors.toList());
        for (Sku sku : skus) {
            sku.material = materialMap.get(sku.data.getMaterialId());
            sku.images = imagesMap.getOrDefault(sku.data.getId(), new ArrayList<>());
            sku.specs = specsMap.get(sku.data.getId());
            sku.prices = priceMap.getOrDefault(sku.data.getId(), new ArrayList<>());
        }
        return skus;
    }

    public SkuDTO getDTO() {
        SkuDTO result = CoreDomainDtoConvert.INSTANCE.convert(data);
        List<SkuPriceData> priceDataList = new ArrayList<>();
        if (prices != null && !prices.isEmpty()) {
            priceDataList = prices.stream().map(SkuPrice::getData).collect(Collectors.toList());
        }
        result.setRelations(material, images, specs, priceDataList);
        return result;
    }

    public SkuSiteDTO getSiteDTO(MarketChannelRelData channel) {
        SkuSiteDTO result = SiteDtoConvert.INSTANCE.convert(data);
        List<SkuPriceData> priceDataList = new ArrayList<>();
        if (prices != null && !prices.isEmpty()) {
            priceDataList = prices.stream().map(SkuPrice::getData).filter(x -> x.getMarketChannelId().equals(channel.getMarketChannelId())).collect(Collectors.toList());
        }
        BigDecimal price;
        if (priceDataList.isEmpty()) {
            //未设置渠道固定价格,采用渠道加价率
            price = PriceUtil.getMarkupPrice(material.getPrice(), channel.getMarkupRate());
        } else {
            //设置了固定价格，直接采用固定价格
            price = priceDataList.get(0).getPrice();
        }
        result.setRelations(material, images, specs, price);
        return result;
    }

    private void saveChannelPrices(List<SkuPriceCreateDTO> channelPrices) {
        if (channelPrices == null || channelPrices.isEmpty()) {
            return;
        }
        prices = new ArrayList<>();
        channelPrices.forEach(x -> {
            SkuPriceData skuPriceData = new SkuPriceData(this.data.getId(), x.getMarketChannelId(), x.getPrice());
            SkuPrice skuPrice = SkuPrice.newInstance(skuPriceData);
            prices.add(skuPrice);
        });
    }

    private void saveSpecs(List<SkuSpecsRelCreateDTO> specsList, Map<Integer, String> specsTypeMap) {
        if (specsList == null || specsList.isEmpty()) {
            return;
        }
        this.specs = specsList.stream().map(x -> new SkuSpecsData(x.getSpecsTypeId(), x.getSpecs())).collect(Collectors.toList());
        for (SkuSpecsData spec : this.specs) {
            spec.setTypeName(specsTypeMap.get(spec.getTypeId()));
        }
        List<SkuSpecsRelData> relDataList = specsList.stream().map(x -> new SkuSpecsRelData(data.getId(), x.getSpecsTypeId(), x.getSpecs())).collect(Collectors.toList());
        SpringUtil.getBean(SkuRepository.class).saveSpecs(relDataList);
    }

    private void saveImages(List<String> images) {
        if (images == null || images.isEmpty()) {
            return;
        }
        AtomicInteger sort = new AtomicInteger(1);
        List<SkuImageData> dataList = images.stream().map(x -> new SkuImageData(data.getId(), x, sort.getAndIncrement())).collect(Collectors.toList());
        this.images = SkuImage.newInstances(dataList);
    }

    private void removeImages() {
        if (this.images != null && !this.images.isEmpty()) {
            SpringUtil.getBean(SkuRepository.class).removeImages(this.data.getId());
        }
    }

    private void save() {
        data = SpringUtil.getBean(SkuRepository.class).save(data);
    }

    public boolean update(SkuUpdateDTO dto, MaterialData materialData) {
        boolean changed = updateImages(dto.getImages());
        if (!data.getSort().equals(dto.getSort())) {
            changed = true;
            data.setSort(dto.getSort());
        }
        if (updateChannelPrice(dto.getChannelPrices())) {
            changed = true;
        }
        return updateBaseData(dto, materialData, changed);
    }

    private boolean updateChannelPrice(List<SkuPriceCreateDTO> priceCreateDTOS) {
        if (priceCreateDTOS == null) {
            priceCreateDTOS = new ArrayList<>();
        }
        boolean changed = false;
        Map<Integer, BigDecimal> priceMap = priceCreateDTOS.stream().collect(Collectors.toMap(SkuPriceCreateDTO::getMarketChannelId, SkuPriceCreateDTO::getPrice));
        //删除旧渠道或更新价格
        List<SkuPrice> updateModels = new ArrayList<>();
        Set<Integer> oldChannelIds = new HashSet<>();
        for (SkuPrice price : this.prices) {
            Integer channelId = price.getMarketChannelId();
            oldChannelIds.add(channelId);
            if (priceMap.containsKey(channelId)) {
                if (price.updatePrice(priceMap.get(channelId))) {
                    changed = true;
                }
                updateModels.add(price);
            } else {
                price.delete();
                changed = true;
            }
        }
        //插入渠道价格
        List<SkuPriceData> newList = priceMap.keySet().stream().filter(x -> !oldChannelIds.contains(x)).map(x -> new SkuPriceData(this.data.getId(), x, priceMap.get(x))).collect(Collectors.toList());
        if (!newList.isEmpty()) {
            changed = true;
        }
        newList.forEach(x -> {
            SkuPrice skuPrice = SkuPrice.newInstance(x);
            updateModels.add(skuPrice);
        });
        //按顺序返回
        this.prices = updateModels.stream().sorted(Comparator.comparing(SkuPrice::getId)).collect(Collectors.toList());
        return changed;
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
        List<SkuImage> updateModels = new ArrayList<>();
        Set<String> oldImages = new HashSet<>();
        for (SkuImage image : this.images) {
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
        List<SkuImageData> newList = imageSortMap.keySet().stream().filter(x -> !oldImages.contains(x)).map(x -> new SkuImageData(this.data.getId(), x, imageSortMap.get(x))).collect(Collectors.toList());
        List<SkuImage> newModels = SkuImage.newInstances(newList);
        if (!newModels.isEmpty()) {
            changed = true;
        }
        newModels.addAll(updateModels);
        //按顺序返回
        this.images = newModels.stream().sorted(Comparator.comparing(SkuImage::getSort)).collect(Collectors.toList());
        return changed;
    }

    private boolean updateBaseData(SkuUpdateDTO dto, MaterialData materialData, boolean changed) {
        if (!data.getUnit().equals(dto.getUnit())) {
            changed = true;
            data.setUnit(dto.getUnit());
        }
        if (!data.getStock().equals(dto.getStock())) {
            changed = true;
            data.setStock(dto.getStock());
        }
        if (!data.getIsFreeShipping().equals(dto.getIsFreeShipping())) {
            changed = true;
            data.setIsFreeShipping(dto.getIsFreeShipping());
        }
        if (!data.getMoq().equals(dto.getMoq())) {
            changed = true;
            data.setMoq(dto.getMoq());
        }
        if (!data.getState().equals(dto.getState())) {
            changed = true;
            data.setState(dto.getState());
        }
        if (!data.getMaterialId().equals(dto.getMaterialId())) {
            changed = true;
            data.setMaterialId(dto.getMaterialId());
            this.material = materialData;
        }
        List<String> dtoImages = dto.getImages();
        String defaultImage = dtoImages == null || dtoImages.isEmpty() ? "" : dtoImages.get(0);
        if (!defaultImage.equals(data.getDefaultImage())) {
            changed = true;
            data.setDefaultImage(defaultImage);
        }
        if (changed) {
            this.data = SpringUtil.getBean(SkuRepository.class).save(this.data);
        }
        return changed;
    }

    public void delete() {
        removeImages();
        removeSpecs();
        removeChannelPrices();
        SpringUtil.getBean(SkuRepository.class).delete(this.data.getId());
    }

    private void removeChannelPrices() {
        if (this.prices == null) {
            return;
        }
        for (SkuPrice price : this.prices) {
            price.delete();
        }
    }

    private void removeSpecs() {
        SpringUtil.getBean(SkuRepository.class).removeSpecs(this.data.getId());
    }

    public void lockStock(Integer qty) {
        Assert.isTrue(this.data.getStock().compareTo(qty) >= 0, this.data.getCode() + "库存不足");
        this.data.setStock(this.data.getStock() - qty);
        this.data.setSalesVolume(this.data.getSalesVolume() + qty);
        save();
    }

    public void releaseStock(Integer qty) {
        this.data.setStock(this.data.getStock() + qty);
        this.data.setSalesVolume(this.data.getSalesVolume() - qty);
        save();
    }

    public Integer getId() {
        return this.data.getId();
    }

    public EnableStateEnum getState() {
        return data.getState();
    }

    public Integer getSort() {
        return data.getSort();
    }

    public Integer getMaterialId() {
        return data.getMaterialId();
    }
}
