package org.geely.service.impl;

import com.alibaba.fastjson2.JSON;
import org.geely.common.constants.BaseServiceApi;
import org.geely.common.model.BaseUserResp;
import org.geely.common.model.IdaasUser;
import org.geely.common.utils.HttpUtil;
import org.geely.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Profile("!dev")
@Service
public class TokenServiceImpl implements TokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Value("${baseService.host}")
    private String host;

    @Override
    public IdaasUser getUserInfo(String token) {

        Map<String, String> querys = new HashMap<>();
        querys.put("access_token", token);
        BaseUserResp baseUserResp;
        try {
            String response = HttpUtil.doGet(host, BaseServiceApi.USER_INFO, null, querys);
            baseUserResp = JSON.parseObject(response, BaseUserResp.class);
        } catch (Exception e) {
            LOGGER.error("TokenServiceImpl.getUserInfo exception", e);
            return null;
        }

        if (baseUserResp != null && baseUserResp.isSuccess()) {
            return baseUserResp.getData();
        }
        return null;
    }
}
