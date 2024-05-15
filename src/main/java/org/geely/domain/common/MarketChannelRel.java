package org.geely.domain.common;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.EqualsAndHashCode;
import org.geely.common.enums.PlatformTypeEnum;
import org.geely.controller.dto.MarketChannelRelDTO;
import org.geely.domain.common.data.MarketChannelRelData;
import org.geely.infrastructure.db.convert.SupportDomainDtoConvert;
import org.geely.infrastructure.db.repository.CommonDomainRepository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 销售渠道关联
 *
 * @author ricardo zhou
 */
@EqualsAndHashCode
public class MarketChannelRel {
    private MarketChannelRelData data;

    private MarketChannelRel(MarketChannelRelData data) {
        this.data = ObjectUtil.clone(data);
    }

    public static MarketChannelRel newInstance(MarketChannelRelData data) {
        MarketChannelRel instance = new MarketChannelRel(data);
        instance.data.setVersion(0);
        instance.save();
        return instance;
    }

    public static List<MarketChannelRel> instanceOf(PlatformTypeEnum platformType, Integer platformId) {
        Set<MarketChannelRelData> dataSet = SpringUtil.getBean(CommonDomainRepository.class).getMarketChannelRel(platformType, platformId);
        return dataSet.stream().map(MarketChannelRel::new).collect(Collectors.toList());
    }

    public static Map<Integer,List<MarketChannelRel>> instancesOf(PlatformTypeEnum platformType, Set<Integer> platformIds) {
        Set<MarketChannelRelData> dataSet = SpringUtil.getBean(CommonDomainRepository.class).listMarketChannelRel(platformType, null,  platformIds);
        return dataSet.stream().map(MarketChannelRel::new).collect(Collectors.groupingBy(MarketChannelRel::getPlatformId));
    }

    public static Set<MarketChannelRel> listByChannelId(PlatformTypeEnum platformType, Integer channelId) {
        Set<MarketChannelRelData> dataSet = SpringUtil.getBean(CommonDomainRepository.class).listMarketChannelRel(platformType, channelId, new HashSet<>());
        return dataSet.stream().map(MarketChannelRel::new).collect(Collectors.toSet());
    }

    public static Boolean matchAll(PlatformTypeEnum platformType, Set<Integer> platformIds, Integer channelId) {
        Set<MarketChannelRelData> dataSet = SpringUtil.getBean(CommonDomainRepository.class).listMarketChannelRel(platformType, channelId, new HashSet<>());
        return dataSet.stream().map(MarketChannelRelData::getPlatformId).collect(Collectors.toSet()).size() == platformIds.size();
    }

    public static void batchAssociate(PlatformTypeEnum platformType, Integer platformId, Set<MarketChannel> marketChannels) {
        if (marketChannels == null || marketChannels.isEmpty()) {
            return;
        }
        Set<Integer> marketChannelIds = marketChannels.stream().map(MarketChannel::getId).collect(Collectors.toSet());
        MarketChannelRel.instanceOf(platformType, platformId).forEach(marketChannelRel -> {
            // 如果关联的渠道不在传入的渠道列表中，则删除
            if (!marketChannelIds.contains(marketChannelRel.getMarketChannelId())) {
                marketChannelRel.delete();
            } else {
                // 如果关联的渠道在传入的渠道列表中，则从列表中移除
                marketChannelIds.remove(marketChannelRel.getMarketChannelId());
            }
        });
        marketChannelIds.forEach(marketChannelId -> {
            MarketChannelRelData marketChannelRelData = new MarketChannelRelData();
            marketChannelRelData.setPlatformType(platformType);
            marketChannelRelData.setPlatformId(platformId);
            marketChannelRelData.setMarketChannelId(marketChannelId);
            marketChannelRelData.setVersion(0);
            MarketChannelRel.newInstance(marketChannelRelData);
        });
    }

    public static List<MarketChannelRel> instancesOfShop(Integer shopId) {
        return SpringUtil.getBean(CommonDomainRepository.class).listMarketChannelByShopId(shopId).stream().map(MarketChannelRel::new).collect(Collectors.toList());
    }

    public void save() {
        this.data = SpringUtil.getBean(CommonDomainRepository.class).saveMarketChannelRel(this.data);
    }

    public void delete() {
        SpringUtil.getBean(CommonDomainRepository.class).deleteMarketChannelRel(this.data.getId());
        this.data = null;
    }

    public MarketChannelRelData getData() {
        return ObjectUtil.clone(this.data);
    }

    public Integer getMarketChannelId() {
        return this.data.getMarketChannelId();
    }

    public Integer getPlatformId() {
        return this.data.getPlatformId();
    }
    public MarketChannelRelDTO getDto(){
        return SupportDomainDtoConvert.INSTANCE.convert(data);
    }

    public BigDecimal getMarkupRate() {
        return this.data.getMarkupRate();
    }
}
