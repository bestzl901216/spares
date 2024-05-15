package org.geely.domain.common;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Sets;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.geely.domain.common.data.MarketChannelData;
import org.geely.infrastructure.db.repository.CommonDomainRepository;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 在管理平台维护的销售渠道
 *
 * @author ricardo zhou
 */
@EqualsAndHashCode
public class MarketChannel {
    private MarketChannelData data;

    private MarketChannel(MarketChannelData data) {
        this.data = ObjectUtil.clone(data);
    }

    public static MarketChannel newInstance(MarketChannelData data) {
        MarketChannel instance = new MarketChannel(data);
        instance.data.setId(null);
        instance.data.setVersion(0);
        instance.save();
        return instance;
    }

    public static MarketChannel instanceOf(Integer id) {
        MarketChannelData data = SpringUtil.getBean(CommonDomainRepository.class).getMarketChannelById(id);
        return new MarketChannel(data);
    }

    public static Set<MarketChannel> instanceOf(Set<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return Sets.newHashSet();
        }
        return SpringUtil.getBean(CommonDomainRepository.class).getMarketChannelByIds(ids).stream().map(MarketChannel::new).collect(Collectors.toSet());
    }

    public void save() {
        this.data = SpringUtil.getBean(CommonDomainRepository.class).saveMarketChannel(this.data);
    }

    public void delete() {
        SpringUtil.getBean(CommonDomainRepository.class).deleteMarketChannel(this.data.getId());
        this.data = null;
    }

    public MarketChannelData getData() {
        return ObjectUtil.clone(this.data);
    }

    public MarketChannel updateName(String name) {
        this.data.setName(name);
        this.save();
        return this;
    }

    public MarketChannel updateRemark(String remark) {
        remark = StringUtils.isBlank(remark) ? "" : remark;
        this.data.setRemark(remark);
        this.save();
        return this;
    }

    public Integer getId() {
        return this.data.getId();
    }
}
