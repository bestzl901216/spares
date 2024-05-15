package org.geely.controller.common;

import com.geely.gsapi.GSAPI;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.geely.common.model.ResultJson;
import org.geely.common.model.UserRolePermissionsDTO;
import org.geely.service.PermissionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 权限控制器
 *
 * @author yancheng.guo
 */
@Api(value = "权限接口", tags = "权限接口")
@RestController
@RequestMapping("/permission")
@Validated
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    @ApiOperation("查询用户角色权限列表")
    @GetMapping(value = "/getUserRolePermissions")
    public ResultJson<UserRolePermissionsDTO> getUserRolePermissions(

            @ApiParam(name = "appId", required = true, value = "应用id")
            @RequestParam(name = "appId", required = true) String appId
    ) {
        appId = GSAPI.getSafeDirInstance().verifyNonNullString(appId);
        UserRolePermissionsDTO userRolePermissionsDTO = permissionService.getUserRolePermissions(appId);
        return ResultJson.success(userRolePermissionsDTO);
    }

    @ApiOperation("查询用户是否具有权限")
    @GetMapping(value = "/isUserHasPermission")
    public ResultJson<Boolean> isUserHasPermission(

            @ApiParam(name = "appId", required = true, value = "应用id")
            @RequestParam(name = "appId", required = true) String appId,

            @ApiParam(name = "permissionValue", required = true, value = "权限值")
            @RequestParam(name = "permissionValue", required = true) String permissionValue

    ) {
        appId = GSAPI.getSafeDirInstance().verifyNonNullString(appId);
        permissionValue = GSAPI.getSafeDirInstance().verifyNonNullString(permissionValue);
        boolean hasPermission = permissionService.isUserHasPermission(permissionValue, appId);
        return ResultJson.success(hasPermission);
    }
}
