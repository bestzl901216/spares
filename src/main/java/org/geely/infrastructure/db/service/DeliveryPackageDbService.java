package org.geely.infrastructure.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geely.infrastructure.db.DeliveryPackageDO;
import org.geely.infrastructure.db.mapper.DeliveryPackageMapper;
import org.springframework.stereotype.Component;

/**
 * @author cong huang
 */
@Component
public class DeliveryPackageDbService extends ServiceImpl<DeliveryPackageMapper, DeliveryPackageDO> {
}
