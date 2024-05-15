package org.geely.infrastructure.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geely.infrastructure.db.CustomerDO;
import org.geely.infrastructure.db.mapper.CustomerMapper;
import org.springframework.stereotype.Component;

/**
 * @author cong huang
 */
@Component
public class CustomerDbService extends ServiceImpl<CustomerMapper, CustomerDO> {
}
