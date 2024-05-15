package org.geely.infrastructure.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geely.infrastructure.db.RoleDO;
import org.geely.infrastructure.db.mapper.RoleMapper;
import org.springframework.stereotype.Component;

/**
 * @author cong huang
 */
@Component
public class RoleDbService extends ServiceImpl<RoleMapper, RoleDO> {
}
