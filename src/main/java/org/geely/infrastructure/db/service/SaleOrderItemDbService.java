package org.geely.infrastructure.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geely.infrastructure.db.SaleOrderItemDO;
import org.geely.infrastructure.db.mapper.SaleOrderItemMapper;
import org.springframework.stereotype.Component;

/**
 * @author cong huang
 */
@Component
public class SaleOrderItemDbService extends ServiceImpl<SaleOrderItemMapper, SaleOrderItemDO> {
}
