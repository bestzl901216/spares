package org.geely.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.geely.common.model.*;
import org.geely.service.UserService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @author ricardo zhou
 */
@Slf4j
@Service
@Profile("dev")
public class UserServiceDevImpl implements UserService {

    @Override
    public List<OrgUserDTO> getOrgUsers(Integer pageNo, Integer pageSize, String ouUuid) {
        return new ArrayList<>();
    }

    @Override
    public PageData<List<UserSearchDTO>> getUserPaging(UserPageDTO userPageDTO) {
        return null;
    }

    @Override
    public UserDetailDTO getUserDetail(String externalId) {
        UserDetailDTO userDetailDTO = new UserDetailDTO();
        userDetailDTO.setExternalId("");
        userDetailDTO.setUsername("");
        userDetailDTO.setDisplayName("");
        userDetailDTO.setPhoneNumber("15827068111");
        userDetailDTO.setEmail("");
        userDetailDTO.setLocked(false);
        userDetailDTO.setEnabled(false);
        userDetailDTO.setDescription("");
        userDetailDTO.setExtendFields(new UserDetailDTO.ExtendFieldsBean());
        userDetailDTO.setAvatarUuid(new Object());
        userDetailDTO.setUuid("");
        userDetailDTO.setBelongs(new ArrayList<>());
        return userDetailDTO;
    }

    @Override
    public ResultJson<List<UserAuthAppResp>> listUserApp(String purchaseId) {
        return ResultJson.fail("平台基座接口调用异常");
    }

    @Override
    public ResultJson<List<MenuDTO>> listUserMenu(String purchaseId) {
        return ResultJson.fail("平台基座接口调用异常");
    }

    @Override
    public ResultJson<AppSDKDetailResp> appDetail(String purchaseId) {
        return ResultJson.fail("平台基座接口调用异常");
    }
}
