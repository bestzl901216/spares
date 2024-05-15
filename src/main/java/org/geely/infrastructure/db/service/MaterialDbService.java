package org.geely.infrastructure.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geely.infrastructure.db.MaterialDO;
import org.geely.infrastructure.db.mapper.MaterialMapper;
import org.springframework.stereotype.Component;

/**
 * @author cong huang
 */
@Component
public class MaterialDbService extends ServiceImpl<MaterialMapper, MaterialDO> {
}
