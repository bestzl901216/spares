package org.geely.infrastructure.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geely.infrastructure.db.SupplierDO;
import org.geely.infrastructure.db.mapper.SupplierMapper;
import org.springframework.stereotype.Component;

/**
 * @author cong huang
 */
@Component
public class SupplierDbService extends ServiceImpl<SupplierMapper, SupplierDO> {
}
