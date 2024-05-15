package org.geely.infrastructure.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geely.infrastructure.db.SaleOrderDO;
import org.geely.infrastructure.db.mapper.SaleOrderMapper;
import org.springframework.stereotype.Component;

/**
 * @author cong huang
 */
@Component
public class SaleOrderDbService extends ServiceImpl<SaleOrderMapper, SaleOrderDO> {
}
