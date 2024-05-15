package org.geely.controller.common;

import com.geely.gsapi.GSAPI;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.geely.common.annotation.NoLogin;
import org.geely.common.model.LoginResp;
import org.geely.common.model.ResultJson;
import org.geely.service.LoginSerivice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import static org.geely.common.enums.ResponseEnum.LOGIN_FAIL;


/**
 * sso统一登录控制器
 *
 * @author yancheng.guo
 */
@RestController
@RequestMapping(value = "/sso")
@Api(tags = "sso登录")
public class SsoController {

    @Resource
    private LoginSerivice loginSerivice;

    @ApiOperation("根据授权code登录")
    @GetMapping(value = "/login")
    @NoLogin
    public ResultJson<LoginResp> loginByCode(

            @ApiParam(name = "code", required = true, value = "单点登录授权code")
            @RequestParam(name = "code", required = true) String code

    ) {
        code = GSAPI.getSafeDirInstance().verifyNonNullString(code);
        LoginResp loginResp = loginSerivice.loginByCode(code);
        if (StringUtils.isEmpty(loginResp.getAccessToken())) {
            ResultJson resultJson = new ResultJson(LOGIN_FAIL.getCode(), LOGIN_FAIL.getMessage(), loginSerivice.createLoginRedirectUrl());
            return resultJson;
        }
        return ResultJson.success(loginResp);
    }

    @ApiOperation("登出")
    @GetMapping(value = "/logout")
    @NoLogin
    public ResultJson<String> logout(

            @ApiParam(name = "accessToken", required = true, value = "登录获得的token")
            @RequestParam(name = "accessToken", required = true) String accessToken,
            HttpServletResponse httpResponse

    ) throws Exception {
        accessToken = GSAPI.getSafeDirInstance().verifyNonNullString(accessToken);
        String logoutRedirectUrl = loginSerivice.createLogoutRedirectUrl(accessToken);
        return ResultJson.success(logoutRedirectUrl);
    }
}
