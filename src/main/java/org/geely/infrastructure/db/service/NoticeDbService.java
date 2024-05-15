package org.geely.infrastructure.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geely.infrastructure.db.NoticeDO;
import org.geely.infrastructure.db.mapper.NoticeMapper;
import org.springframework.stereotype.Component;

/**
 * @author ricardo zhou
 */
@Component
public class NoticeDbService extends ServiceImpl<NoticeMapper, NoticeDO> {
}
