package org.geely.infrastructure.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geely.infrastructure.db.VehicleDO;
import org.geely.infrastructure.db.mapper.VehicleMapper;
import org.springframework.stereotype.Component;

/**
 * @author cong huang
 */
@Component
public class VehicleDbService extends ServiceImpl<VehicleMapper, VehicleDO> {
}
