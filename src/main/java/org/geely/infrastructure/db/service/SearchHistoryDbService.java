package org.geely.infrastructure.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geely.infrastructure.db.SearchHistoryDO;
import org.geely.infrastructure.db.mapper.SearchHistoryMapper;
import org.springframework.stereotype.Component;

/**
 * @author ricardo zhou
 */
@Component
public class SearchHistoryDbService extends ServiceImpl<SearchHistoryMapper, SearchHistoryDO> {
}
