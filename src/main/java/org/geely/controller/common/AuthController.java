package org.geely.controller.common;

import com.geely.gsapi.GSAPI;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.geely.common.constants.AuthConstant;
import org.geely.common.enums.ResponseEnum;
import org.geely.common.model.IdaasUser;
import org.geely.common.model.ResultJson;
import org.geely.service.LoginSerivice;
import org.geely.service.TokenService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static org.geely.common.enums.ResponseEnum.USER_NOT_LOGIN;
import static org.geely.common.enums.ResponseEnum.USER_TOKEN_EXPIRED;

/**
 * 登录认证控制器
 *
 * @author yancheng.guo
 */
@Api(value = "认证接口", tags = "认证接口")
@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    @Resource
    private TokenService tokenService;

    @Resource
    private LoginSerivice loginSerivice;

    @GetMapping(value = "/token/check")
    @ApiOperation("校验token")
    @Valid
    public ResultJson<Boolean> checkLogin(
            @RequestHeader(AuthConstant.TOKEN_KEY)
            @NotBlank(message = "token不能为空")
            @ApiParam(name = AuthConstant.TOKEN_KEY, required = true) String token) {
        token = GSAPI.getSafeDirInstance().verifyNonNullString(token);
        IdaasUser idaasUser = tokenService.getUserInfo(token);
        if (null == idaasUser) {
            return ResultJson.fail(ResponseEnum.USER_TOKEN_EXPIRED.getMessage());
        }
        return ResultJson.success(Boolean.TRUE);
    }

    @GetMapping(value = "/user/info")
    @ApiOperation("用户信息获取")
    @Valid
    public ResultJson<IdaasUser> getUserInfo(
            @RequestHeader(name = AuthConstant.TOKEN_KEY, required = false)
            @ApiParam(name = AuthConstant.TOKEN_KEY, required = false) String token) {
        if (StringUtils.isBlank(token)) {
            ResultJson resultJson = new ResultJson<>(USER_NOT_LOGIN.getCode(), USER_NOT_LOGIN.getMessage(), loginSerivice.createLoginRedirectUrl());
            return resultJson;
        } else {
            token = GSAPI.getSafeDirInstance().verifyNonNullString(token);
            IdaasUser idaasUser = tokenService.getUserInfo(token);
            if (null == idaasUser) {
                ResultJson resultJson = new ResultJson<>(USER_TOKEN_EXPIRED.getCode(), USER_TOKEN_EXPIRED.getMessage(), loginSerivice.createLoginRedirectUrl());
                return resultJson;
            }
            return ResultJson.success(idaasUser);
        }

    }
}
