package org.geely.infrastructure.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geely.infrastructure.db.CustomerAddressDO;
import org.geely.infrastructure.db.mapper.CustomerAddressMapper;
import org.springframework.stereotype.Component;

/**
 * @author cong huang
 */
@Component
public class CustomerAddressDbService extends ServiceImpl<CustomerAddressMapper, CustomerAddressDO> {
}
