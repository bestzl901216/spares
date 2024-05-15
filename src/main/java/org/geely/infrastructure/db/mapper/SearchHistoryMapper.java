package org.geely.infrastructure.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.geely.infrastructure.db.SearchHistoryDO;

/**
 * @author ricardo zhou
 */
@Mapper
public interface SearchHistoryMapper extends BaseMapper<SearchHistoryDO> {
}
