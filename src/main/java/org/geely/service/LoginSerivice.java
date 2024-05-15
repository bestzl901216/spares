package org.geely.service;

import org.geely.common.model.LoginResp;

/**
 * 登录相关接口
 * @author yancheng.guo
 */
public interface LoginSerivice {

    /**
     * 生成登录跳转的url
     * @return
     */
    String createLoginRedirectUrl();

    /**
     * 根据授权code登录
     * @param code
     * @return
     */
    LoginResp loginByCode(String code);

    /**
     * 生成登出跳转的url
     * @param accessToken
     * @return
     */
    String createLogoutRedirectUrl(String accessToken);

}
