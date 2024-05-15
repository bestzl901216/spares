package org.geely.infrastructure.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geely.infrastructure.db.MessageConsumeLogDO;
import org.geely.infrastructure.db.mapper.MessageConsumeLogMapper;
import org.springframework.stereotype.Component;

/**
 * @author ricardo zhou
 */
@Component
public class MessageConsumeLogDbService extends ServiceImpl<MessageConsumeLogMapper, MessageConsumeLogDO> {
}
