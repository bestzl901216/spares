package org.geely.domain.core;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.geely.domain.core.data.SkuPriceData;
import org.geely.infrastructure.db.repository.SkuPriceRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 67091
 */
public class SkuPrice {
    private SkuPriceData data;

    private SkuPrice(SkuPriceData data) {
        this.data = ObjectUtil.clone(data);
    }

    public static SkuPrice newInstance(SkuPriceData data) {
        SkuPrice instance = new SkuPrice(data);
        instance.data.setId(null);
        instance.data.setVersion(0);
        instance.save();
        return instance;
    }
    public static List<SkuPrice> instancesOf(Set<Integer> skuIds) {
        List<SkuPriceData> dataList =  SpringUtil.getBean(SkuPriceRepository.class).listBySkuIds(skuIds);
        return  dataList.stream().map(SkuPrice::new).collect(Collectors.toList());
    }

    private void save() {
        data = SpringUtil.getBean(SkuPriceRepository.class).save(data);
    }

    public SkuPriceData getData() {
        return ObjectUtil.clone(data);
    }

    public void delete() {
        SpringUtil.getBean(SkuPriceRepository.class).delete(data);
        data = null;
    }

    public Integer getSkuId(){
        return data.getSkuId();
    }

    public boolean updatePrice(BigDecimal newPrice) {
        if(data.getPrice().equals(newPrice)) {
            return false;
        }
        data.setPrice(newPrice);
        data = SpringUtil.getBean(SkuPriceRepository.class).save(data);
        return true;
    }

    public Integer getMarketChannelId() {
        return data.getMarketChannelId();
    }

    public Integer getId() {
        return data.getId();
    }
}
