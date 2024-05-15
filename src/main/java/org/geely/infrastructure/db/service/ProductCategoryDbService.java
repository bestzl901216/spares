package org.geely.infrastructure.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geely.infrastructure.db.ProductCategoryDO;
import org.geely.infrastructure.db.mapper.ProductCategoryMapper;
import org.springframework.stereotype.Component;

/**
 * @author cong huang
 */
@Component
public class ProductCategoryDbService extends ServiceImpl<ProductCategoryMapper, ProductCategoryDO> {
}
