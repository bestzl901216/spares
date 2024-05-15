package org.geely.common.model;

import lombok.Data;

import java.util.List;

@Data
public class UserRolePermissionsDTO {

    private String username;
    // 角色列表
    private List<RolePermissionsBean> rolePermissions;
    // 权限列表
    private List<PermissionsBean> permissions;
    private List<?> roles;

    @Data
    public static class RolePermissionsBean {
        /**
         * uuid : 0b5daec6b4e9c0822224ea31eb63d69b6pXPaLZz9Cm
         * name : it
         * permissionValue : it
         * remark :
         * enabled : true
         * permissions : []
         */

        private String uuid;
        private String name;
        private String permissionValue;
        private String remark;
        private boolean enabled;
        private List<?> permissions;
    }

    @Data
    public static class PermissionsBean {
        /**
         * uuid : af5d6b0508d437fe90750b2259bba16ajA1q0XSD9R2
         * parentPermissionUuid : f4ebfd04e314051ce9e7b48bf19fe4d31hIpzGjGjP1
         * name : 按钮模型
         * permissionValue : anmx
         * relationUrl : null
         * remark : null
         * type : data
         * dataAccessRules : []
         */

        private String uuid;
        private String parentPermissionUuid;
        private String name;
        private String permissionValue;
        private String relationUrl;
        private String remark;
        private String type;
        private List<?> dataAccessRules;
    }
}