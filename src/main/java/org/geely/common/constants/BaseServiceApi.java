package org.geely.common.constants;

/**
 * 基座服务api
 *
 * @author yancheng.guo
 */
public class BaseServiceApi {
    private BaseServiceApi() {
    }

    /**
     * 登录
     */
    public static final String LOGIN = "/api/v1/sdk/login";

    /**
     * 登出
     */
    public static final String LOGOUT = "/api/v1/sdk/logout";

    /**
     * 根据token获取用户信息
     */
    public static final String USER_INFO = "/api/v1/sdk/info";

    /**
     * 根据授权code获取accessToken
     */
    public static final String ACCESS_TOKEN = "/api/v1/sdk/access-token";

    /**
     * 查询组织详情
     */
    public static final String ORG_DETAIL = "/api/v1/sdk/getOrgDetail";

    /**
     * 查询子组织
     */
    public static final String CHILD_ORG = "/api/v1/sdk/getChildOrg";

    /**
     * 查询应用菜单
     */
    public static final String APP_MENU = "/api/v1/sdk/getAppMenu";

    /**
     * 查询组织下的用户
     */
    public static final String ORG_USERS = "/api/v1/sdk/getOrgUsers";

    /**
     * 查询用户列表
     */
    public static final String USER_PAGING = "/api/v1/sdk/getUserPaging";

    /**
     * 查询用户详情
     */
    public static final String USER_DETAIL = "/api/v1/sdk/getUserDetail";


    /**
     * 获取平台及平台下应用
     */
    public static final String USER_APPS = "/api/v1/sdk/listUserApp";

    /**
     * 获取用户授权平台菜单
     */
    public static final String USER_MENUS = "/api/v1/sdk/listUserMenu";

    /**
     * 查询用户角色和权限列表
     */
    public static final String USER_ROLE_PERMISSIONS = "/api/v1/sdk/getUserRolePermissions";

    /**
     * 查询用户是否具有权限
     */
    public static final String IS_USER_HAS_PERMISSION = "/api/v1/sdk/isUserHasPermission";

    /**
     * 查询应用信息
     */
    public static final String APP_DETAIL = "/api/v1/sdk/appDetail";

    /**
     * 保存操作日志
     */
    public static final String OPERATE_LOG_SAVE = "/api/v1/sdk/saveOperateLog";

}
