package org.geely.infrastructure.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.geely.infrastructure.db.ProductDO;

/**
 * @author cong huang
 */
@Mapper
public interface ProductMapper extends BaseMapper<ProductDO> {
}
