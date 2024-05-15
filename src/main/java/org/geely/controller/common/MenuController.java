package org.geely.controller.common;

import com.geely.gsapi.GSAPI;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.geely.common.model.MenuDTO;
import org.geely.common.model.ResultJson;
import org.geely.service.MenuService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单控制器
 *
 * @author yancheng.guo
 */
@Api(value = "菜单接口", tags = "菜单接口")
@RestController
@RequestMapping("/menu")
@Validated
public class MenuController {

    @Resource
    private MenuService menuService;

    @ApiOperation("查询应用菜单")
    @GetMapping(value = "/getAppMenu")
    public ResultJson<List<MenuDTO>> getAppMenu(
            @ApiParam(name = "menuType", required = true, value = "类型 1-顶部功能 2-顶部导航 3-侧边菜单")
            @RequestParam(name = "menuType", required = true) Integer menuType,

            @ApiParam(name = "appId", required = true, value = "应用id")
            @RequestParam(name = "appId", required = true) String appId
    ) {
        appId= GSAPI.getSafeDirInstance().verifyNonNullString(appId);
        List<MenuDTO> list = menuService.getAppMenu(menuType, appId);
        return ResultJson.success(list);
    }

    @ApiOperation("查询用户菜单")
    @GetMapping(value = "/getUserMenu")
    public ResultJson<List<MenuDTO>> getUserMenu(

            @ApiParam(name = "menuType", required = true, value = "类型 1-顶部功能 2-顶部导航 3-侧边菜单")
            @RequestParam(name = "menuType", required = false) Integer menuType,

            @ApiParam(name = "appId", required = true, value = "应用id")
            @RequestParam(name = "appId", required = true) String appId) {

        appId= GSAPI.getSafeDirInstance().verifyNonNullString(appId);
        List<MenuDTO> list = menuService.getUserMenu(menuType, appId);
        return ResultJson.success(list);
    }
}
