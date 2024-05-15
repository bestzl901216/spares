package org.geely.infrastructure.db.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.geely.infrastructure.db.CartDO;
import org.geely.infrastructure.db.mapper.CartMapper;
import org.springframework.stereotype.Component;

/**
 * @author cong huang
 */
@Component
public class CartDbService extends ServiceImpl<CartMapper, CartDO> {
}
