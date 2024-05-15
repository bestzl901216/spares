package org.geely.infrastructure.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geely.infrastructure.db.CustomerInvoiceDO;
import org.geely.infrastructure.db.mapper.CustomerInvoiceMapper;
import org.springframework.stereotype.Component;

/**
 * @author cong huang
 */
@Component
public class CustomerInvoiceDbService extends ServiceImpl<CustomerInvoiceMapper, CustomerInvoiceDO> {
}
