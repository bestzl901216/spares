package org.geely.service.impl;

import com.alibaba.fastjson2.JSON;
import org.geely.common.constants.BaseServiceApi;
import org.geely.common.model.*;
import org.geely.common.utils.HttpUtil;
import org.geely.service.LoginSerivice;
import org.geely.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginSerivice {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Value("${baseService.host}")
    private String host;

    @Value("${baseService.appId}")
    private String appId;

    @Resource
    private TokenService tokenService;

    private static final String APP_ID = "appId";

    @Override
    public String createLoginRedirectUrl() {
        Map<String, String> querys = new HashMap<>();
        querys.put(APP_ID, appId);
        BaseLoginResp baseLoginResp = null;
        try {
            String response = HttpUtil.doGet(host, BaseServiceApi.LOGIN, null, querys);
            LOGGER.info("createLoginRedirectUrl:{},ret:{}", querys, response);
            baseLoginResp = JSON.parseObject(response, BaseLoginResp.class);
        } catch (Exception e) {
            LOGGER.error("LoginServiceImpl.createLoginRedirectUrl exception", e);
        }

        if (baseLoginResp != null && baseLoginResp.isSuccess()) {
            return baseLoginResp.getData();
        }
        return null;
    }

    @Override
    public LoginResp loginByCode(String code) {
        LoginResp loginResp = new LoginResp();

        Map<String, String> querys = new HashMap<>();
        querys.put(APP_ID, appId);
        querys.put("code", code);
        BaseAccessTokenResp baseAccessTokenResp = null;
        try {
            LOGGER.info("loginByCode:{}", querys);
            String response = HttpUtil.doGet(host, BaseServiceApi.ACCESS_TOKEN, null, querys);
            baseAccessTokenResp = JSON.parseObject(response, BaseAccessTokenResp.class);
        } catch (Exception e) {
            LOGGER.error("LoginServiceImpl.loginByCode exception", e);
        }

        if (baseAccessTokenResp != null && baseAccessTokenResp.isSuccess()) {
            String accessToken = baseAccessTokenResp.getData();
            if (StringUtils.hasText(accessToken)) {
                loginResp.setAccessToken(accessToken);

                //根据token查询用户信息
                IdaasUser idaasUser = tokenService.getUserInfo(accessToken);
                loginResp.setIdaasUser(idaasUser);
            }
        }
        return loginResp;
    }

    public String createLogoutRedirectUrl(String accessToken) {
        Map<String, String> querys = new HashMap<>();
        querys.put(APP_ID, appId);
        querys.put("accessToken", accessToken);
        BaseLogoutResp baseLogoutResp = null;
        try {
            LOGGER.info("createLogoutRedirectUrl:{}", querys);
            String response = HttpUtil.doGet(host, BaseServiceApi.LOGOUT, null, querys);
            baseLogoutResp = JSON.parseObject(response, BaseLogoutResp.class);
        } catch (Exception e) {
            LOGGER.error("LoginServiceImpl.createLogoutRedirectUrl exception", e);
        }

        if (baseLogoutResp != null && baseLogoutResp.isSuccess()) {
            return baseLogoutResp.getData();
        }
        return null;
    }
}
