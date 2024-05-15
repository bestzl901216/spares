package org.geely.infrastructure.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geely.infrastructure.db.SaleOrderFlowDO;
import org.geely.infrastructure.db.mapper.SaleOrderFlowMapper;
import org.springframework.stereotype.Component;

/**
 * @author ricardo zhou
 */
@Component
public class SaleOrderFlowDbService extends ServiceImpl<SaleOrderFlowMapper, SaleOrderFlowDO> {
}
