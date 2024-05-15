package org.geely.infrastructure.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geely.infrastructure.db.MallBankAccountDO;
import org.geely.infrastructure.db.mapper.MallBankAccountMapper;
import org.springframework.stereotype.Component;

/**
 * @author cong huang
 */
@Component
public class MallBankAccountDbService extends ServiceImpl<MallBankAccountMapper, MallBankAccountDO> {
}
