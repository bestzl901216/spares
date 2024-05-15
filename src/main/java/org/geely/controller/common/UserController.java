package org.geely.controller.common;

import com.geely.gsapi.GSAPI;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.geely.common.model.*;
import org.geely.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 用户控制器
 *
 * @author yancheng.guo
 */
@Api(value = "用户接口", tags = "用户接口")
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Resource
    private UserService userService;

    @ApiOperation("查询用户列表")
    @PostMapping(value = "/getUserPaging")
    public ResultJson<PageData<List<UserSearchDTO>>> getUserPaging(@RequestBody @Valid UserPageDTO req) {

        PageData<List<UserSearchDTO>> pageData = userService.getUserPaging(req);
        return ResultJson.success(pageData);
    }

    @ApiOperation("查询用户详情")
    @GetMapping(value = "/getUserDetail")
    public ResultJson<UserDetailDTO> getUserDetail(

            @ApiParam(name = "externalId", required = true, value = "用户外部id")
            @RequestParam(name = "externalId", required = true) String externalId) {
        externalId = GSAPI.getSafeDirInstance().verifyNonNullString(externalId);
        UserDetailDTO userDetailDTO = userService.getUserDetail(externalId);
        return ResultJson.success(userDetailDTO);
    }

    @ApiOperation("获取用户授权的平台及平台下应用")
    @GetMapping(value = "/listUserApp")
    public ResultJson<List<UserAuthAppResp>> listUserApp(@ApiParam(name = "appId", required = false, value = "应用ID")
                                                         @RequestParam(name = "appId", required = false) String appId) {
        return userService.listUserApp(appId);
    }


    @ApiOperation("获取用户授权的平台顶部菜单")
    @GetMapping(value = "/listUserMenu")
    public ResultJson<List<MenuDTO>> listUserMenu(@ApiParam(name = "appId", required = false, value = "应用ID")
                                                  @RequestParam(name = "appId", required = false) String appId) {
        return userService.listUserMenu(appId);
    }

    @ApiOperation("应用详情")
    @GetMapping(value = "/appDetail")
    public ResultJson<AppSDKDetailResp> appDetail(
            @ApiParam(name = "appId", required = true, value = "应用id")
            @RequestParam(name = "appId", required = true) String appId) {
        appId = GSAPI.getSafeDirInstance().verifyNonNullString(appId);
        return userService.appDetail(appId);
    }
}
