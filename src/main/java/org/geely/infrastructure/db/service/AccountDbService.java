package org.geely.infrastructure.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geely.infrastructure.db.AccountDO;
import org.geely.infrastructure.db.mapper.AccountMapper;
import org.springframework.stereotype.Component;

/**
 * @author ricardo zhou
 */
@Component
public class AccountDbService extends ServiceImpl<AccountMapper, AccountDO> {
}
