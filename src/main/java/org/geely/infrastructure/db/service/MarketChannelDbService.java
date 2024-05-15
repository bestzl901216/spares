package org.geely.infrastructure.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geely.infrastructure.db.MarketChannelDO;
import org.geely.infrastructure.db.mapper.MarketChannelMapper;
import org.springframework.stereotype.Component;

/**
 * @author cong huang
 */
@Component
public class MarketChannelDbService extends ServiceImpl<MarketChannelMapper, MarketChannelDO> {
}
