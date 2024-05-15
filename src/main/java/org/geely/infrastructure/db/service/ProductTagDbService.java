package org.geely.infrastructure.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geely.infrastructure.db.ProductTagDO;
import org.geely.infrastructure.db.mapper.ProductTagMapper;
import org.springframework.stereotype.Component;

/**
 * @author cong huang
 */
@Component
public class ProductTagDbService extends ServiceImpl<ProductTagMapper, ProductTagDO> {
}
