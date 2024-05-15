package org.geely.infrastructure.db.gateway;

import org.apache.ibatis.annotations.Mapper;
import org.geely.controller.dto.AccountDTO;

/**
 * @author ricardo zhou
 */
@Mapper
public interface AccountDbGateway {
    /**
     * 商城首页广告类目列表
     *
     * @param accountId 账号id
     * @return 账号信息
     */
    AccountDTO getAccountById(Integer accountId);
}
