package org.geely.infrastructure.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geely.infrastructure.db.ProductDO;
import org.geely.infrastructure.db.mapper.ProductMapper;
import org.springframework.stereotype.Component;

/**
 * @author cong huang
 */
@Component
public class ProductDbService extends ServiceImpl<ProductMapper, ProductDO> {
}
