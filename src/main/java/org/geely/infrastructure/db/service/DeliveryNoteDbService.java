package org.geely.infrastructure.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geely.infrastructure.db.DeliveryNoteDO;
import org.geely.infrastructure.db.mapper.DeliveryNoteMapper;
import org.springframework.stereotype.Component;

/**
 * @author cong huang
 */
@Component
public class DeliveryNoteDbService extends ServiceImpl<DeliveryNoteMapper, DeliveryNoteDO> {
}
