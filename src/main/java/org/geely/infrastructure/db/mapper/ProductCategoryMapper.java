package org.geely.infrastructure.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.geely.infrastructure.db.ProductCategoryDO;

/**
 * @author cong huang
 */
@Mapper
public interface ProductCategoryMapper extends BaseMapper<ProductCategoryDO> {
}
