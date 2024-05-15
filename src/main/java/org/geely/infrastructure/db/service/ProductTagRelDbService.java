package org.geely.infrastructure.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geely.infrastructure.db.ProductTagRelDO;
import org.geely.infrastructure.db.mapper.ProductTagRelMapper;
import org.springframework.stereotype.Component;

/**
 * @author cong huang
 */
@Component
public class ProductTagRelDbService extends ServiceImpl<ProductTagRelMapper, ProductTagRelDO> {
}
