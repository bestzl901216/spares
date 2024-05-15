package org.geely.service.impl;

import com.alibaba.fastjson2.JSON;
import org.geely.common.constants.BaseServiceApi;
import org.geely.common.model.BaseUserHasPermissionResp;
import org.geely.common.model.BaseUserRolePermissionResp;
import org.geely.common.model.UserRolePermissionsDTO;
import org.geely.common.session.SessionContext;
import org.geely.common.utils.HttpUtil;
import org.geely.service.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PermissionServiceImpl implements PermissionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionServiceImpl.class);


    @Value("${baseService.host}")
    private String host;

    @Override
    public UserRolePermissionsDTO getUserRolePermissions(String appId) {
        Map<String, String> querys = new HashMap<>();
        querys.put("appId", appId);
        querys.put("username", SessionContext.getIdaasUser().getUsername());
        BaseUserRolePermissionResp baseUserRolePermissionResp = null;
        try {
            String response = HttpUtil.doGet(host, BaseServiceApi.USER_ROLE_PERMISSIONS, null, querys);
            baseUserRolePermissionResp = JSON.parseObject(response, BaseUserRolePermissionResp.class);
        } catch (Exception e) {
            LOGGER.error("getUserRolePermissions exception", e);
        }

        if (baseUserRolePermissionResp != null && baseUserRolePermissionResp.isSuccess()) {
            return baseUserRolePermissionResp.getData();
        }
        return null;
    }

    @Override
    public boolean isUserHasPermission(String permissionValue, String appId) {
        return this.isUserHasPermission(permissionValue, appId, SessionContext.getIdaasUser().getUsername());
    }

    @Override
    public boolean isUserHasPermission(String permissionValue, String appId, String username) {
        Map<String, String> querys = new HashMap<>();
        querys.put("appId", appId);
        querys.put("username", username);
        querys.put("permissionValue", permissionValue);
        BaseUserHasPermissionResp baseUserHasPermissionResp = null;
        try {
            String response = HttpUtil.doGet(host, BaseServiceApi.IS_USER_HAS_PERMISSION, null, querys);
            baseUserHasPermissionResp = JSON.parseObject(response, BaseUserHasPermissionResp.class);
        } catch (Exception e) {
            LOGGER.error("getUserRolePermissions exception", e);
        }

        if (baseUserHasPermissionResp != null && baseUserHasPermissionResp.isSuccess()) {
            return baseUserHasPermissionResp.getData();
        }
        return false;
    }
}
