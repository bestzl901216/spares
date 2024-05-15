package org.geely.infrastructure.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.geely.infrastructure.db.CustomerAddressDO;

/**
 * @author cong huang
 */
@Mapper
public interface CustomerAddressMapper extends BaseMapper<CustomerAddressDO> {
}
