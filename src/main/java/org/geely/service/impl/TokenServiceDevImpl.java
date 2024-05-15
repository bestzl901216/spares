package org.geely.service.impl;

import org.geely.common.model.IdaasUser;
import org.geely.service.TokenService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;


/**
 * 开发环境下返回默认用户
 *
 * @author ricardo zhou
 */
@Profile("dev")
@Service
public class TokenServiceDevImpl implements TokenService {

    @Override
    public IdaasUser getUserInfo(String token) {
        IdaasUser idaasUser = new IdaasUser();
        idaasUser.setSub("3993326077301050171");
        idaasUser.setOu_id("7759147560545495208");
        idaasUser.setNickname("默认管理员");
        idaasUser.setOu_name("吉利0724");
        idaasUser.setUsername("Geely_2020");
        return idaasUser;
    }
}
