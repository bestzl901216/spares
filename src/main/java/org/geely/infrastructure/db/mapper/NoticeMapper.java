package org.geely.infrastructure.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.geely.infrastructure.db.NoticeDO;

/**
 * @author ricardo zhou
 */
@Mapper
public interface NoticeMapper extends BaseMapper<NoticeDO> {
}
