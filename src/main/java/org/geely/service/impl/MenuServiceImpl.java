package org.geely.service.impl;

import com.alibaba.fastjson2.JSON;
import org.geely.common.constants.BaseServiceApi;
import org.geely.common.model.BaseMenuResp;
import org.geely.common.model.MenuDTO;
import org.geely.common.session.SessionContext;
import org.geely.common.utils.HttpUtil;
import org.geely.common.utils.ThreadPoolUtils;
import org.geely.service.MenuService;
import org.geely.service.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class MenuServiceImpl implements MenuService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Value("${baseService.host}")
    private String host;

    @Resource
    private PermissionService permissionService;

    @Override
    public List<MenuDTO> getAppMenu(Integer menuType, String appId) {
        Map<String, String> querys = new HashMap<>();
        querys.put("appId", appId);
        if (menuType != null) {
            querys.put("menuType", String.valueOf(menuType));
        }
        BaseMenuResp baseMenuResp = null;
        try {
            String response = HttpUtil.doGet(host, BaseServiceApi.APP_MENU, null, querys);
            baseMenuResp = JSON.parseObject(response, BaseMenuResp.class);
        } catch (Exception e) {
            LOGGER.error("getAppMenu exception", e);
        }

        if (baseMenuResp != null && baseMenuResp.isSuccess()) {
            return baseMenuResp.getData();
        }
        return new ArrayList<>();
    }

    @Override
    public List<MenuDTO> getUserMenu(Integer menuType, String appId) {
        List<MenuDTO> userMenuList = new ArrayList<>();
        List<CompletableFuture<MenuDTO>> tasks = new ArrayList<>();
        List<MenuDTO> menuList = getAppMenu(menuType, appId);
        String username = SessionContext.getIdaasUser().getUsername();
        for (MenuDTO menuDTO : menuList) {
            CompletableFuture<MenuDTO> task = CompletableFuture.supplyAsync(() -> {
                boolean hasPermission = permissionService.isUserHasPermission(menuDTO.getPermissionValue(), appId, username);
                if (hasPermission) {
                    return menuDTO;
                } else {
                    return null;
                }
            }, ThreadPoolUtils.OPERATE_LOG_THREAD_POOL);
            tasks.add(task);
        }
        tasks.forEach(e -> {
            if (e.join() != null) {
                userMenuList.add(e.join());
            }
        });
        return userMenuList;
    }

}
