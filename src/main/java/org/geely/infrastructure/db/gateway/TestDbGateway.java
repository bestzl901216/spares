package org.geely.infrastructure.db.gateway;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author ricardo zhou
 */
@Mapper
public interface TestDbGateway {
    String test();
}
