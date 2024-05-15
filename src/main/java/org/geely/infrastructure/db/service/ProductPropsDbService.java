package org.geely.infrastructure.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geely.infrastructure.db.ProductPropsDO;
import org.geely.infrastructure.db.mapper.ProductPropsMapper;
import org.springframework.stereotype.Component;

/**
 * @author cong huang
 */
@Component
public class ProductPropsDbService extends ServiceImpl<ProductPropsMapper, ProductPropsDO> {
}
