package org.geely.infrastructure.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geely.infrastructure.db.SkuDO;
import org.geely.infrastructure.db.mapper.SkuMapper;
import org.springframework.stereotype.Component;

/**
 * @author cong huang
 */
@Component
public class SkuDbService extends ServiceImpl<SkuMapper, SkuDO> {
}
