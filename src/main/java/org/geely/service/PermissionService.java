package org.geely.service;

import org.geely.common.model.UserRolePermissionsDTO;

public interface PermissionService {

    /**
     * 查询用户角色和权限列表
     * @return
     */
    UserRolePermissionsDTO getUserRolePermissions(String appId);

    /**
     * 查询用户是否具有权限
     * @param permissionValue
     * @return
     */
    boolean isUserHasPermission(String permissionValue,String appId);


    /**
     * 查询用户是否具有权限
     * @param permissionValue
     * @return
     */
    boolean isUserHasPermission(String permissionValue,String appId,String username);
}
