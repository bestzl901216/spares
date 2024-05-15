package org.geely.service;

import org.geely.common.model.*;

import java.util.List;

/**
 * 用户接口
 *
 * @author yancheng.guo
 */
public interface UserService {

    /**
     * 获取用户下组织
     *
     * @param ouUuid
     * @return
     */
    List<OrgUserDTO> getOrgUsers(Integer pageNo, Integer pageSize, String ouUuid);

    /**
     * 查询用户列表
     *
     * @return
     */
    PageData<List<UserSearchDTO>> getUserPaging(UserPageDTO userPageDTO);

    /**
     * 获取用户详情
     *
     * @param externalId
     * @return
     */
    UserDetailDTO getUserDetail(String externalId);

    /**
     * 查询用户授权的应用
     *
     * @return
     */
    ResultJson<List<UserAuthAppResp>> listUserApp(String appId);

    /**
     * 查询
     *
     * @return
     */
    ResultJson<List<MenuDTO>> listUserMenu(String appId);

    ResultJson<AppSDKDetailResp> appDetail(String appId);
}
