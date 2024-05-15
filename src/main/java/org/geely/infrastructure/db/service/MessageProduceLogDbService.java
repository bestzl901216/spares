package org.geely.infrastructure.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geely.infrastructure.db.MessageProduceLogDO;
import org.geely.infrastructure.db.mapper.MessageProduceLogMapper;
import org.springframework.stereotype.Component;

/**
 * @author ricardo zhou
 */
@Component
public class MessageProduceLogDbService extends ServiceImpl<MessageProduceLogMapper, MessageProduceLogDO> {
}
