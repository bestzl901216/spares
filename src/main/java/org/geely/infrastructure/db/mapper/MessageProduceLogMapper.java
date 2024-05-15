package org.geely.infrastructure.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.geely.infrastructure.db.MessageProduceLogDO;

/**
 * @author ricardo zhou
 */
@Mapper
public interface MessageProduceLogMapper extends BaseMapper<MessageProduceLogDO> {
}
