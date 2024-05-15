package org.geely.infrastructure.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geely.infrastructure.db.MallDO;
import org.geely.infrastructure.db.mapper.MallMapper;
import org.springframework.stereotype.Component;

/**
 * @author cong huang
 */
@Component
public class MallDbService extends ServiceImpl<MallMapper, MallDO> {
}
