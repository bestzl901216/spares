package org.geely.service;

import org.geely.common.model.IdaasUser;

/**
 * 会话token接口
 * @author yancheng.guo
 */
public interface TokenService {

    /**
     * 根据token获取idaas用户信息
     * @param token
     * @return
     */
    IdaasUser getUserInfo(String token);
}
