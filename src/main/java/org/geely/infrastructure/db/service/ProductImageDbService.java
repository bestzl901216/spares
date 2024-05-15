package org.geely.infrastructure.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geely.infrastructure.db.ProductImageDO;
import org.geely.infrastructure.db.mapper.ProductImageMapper;
import org.springframework.stereotype.Component;

/**
 * @author cong huang
 */
@Component
public class ProductImageDbService extends ServiceImpl<ProductImageMapper, ProductImageDO> {
}
