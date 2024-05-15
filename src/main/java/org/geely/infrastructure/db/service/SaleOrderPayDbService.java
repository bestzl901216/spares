package org.geely.infrastructure.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geely.infrastructure.db.SaleOrderPayDO;
import org.geely.infrastructure.db.mapper.SaleOrderPayMapper;
import org.springframework.stereotype.Component;

/**
 * @author cong huang
 */
@Component
public class SaleOrderPayDbService extends ServiceImpl<SaleOrderPayMapper, SaleOrderPayDO> {
}
