package org.geely.service.impl;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.geely.common.constants.BaseServiceApi;
import org.geely.common.model.*;
import org.geely.common.session.SessionContext;
import org.geely.common.utils.HttpUtil;
import org.geely.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Profile("!dev")
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${baseService.host}")
    private String host;
    @Value("${baseService.appId}")
    private String appId;

    @Override
    public List<OrgUserDTO> getOrgUsers(Integer pageNo, Integer pageSize, String ouUuid) {
        Map<String, String> querys = new HashMap<>();
        querys.put("ouUuid", ouUuid);
        querys.put("pageNo", String.valueOf(pageNo));
        querys.put("pageSize", String.valueOf(pageSize));
        BaseOrgUsersResp baseOrgUsersResp = null;
        try {
            String response = HttpUtil.doGet(host, BaseServiceApi.ORG_USERS, null, querys);
            baseOrgUsersResp = JSON.parseObject(response, BaseOrgUsersResp.class);
        } catch (Exception e) {
            LOGGER.error("getOrgUsers exception", e);
        }

        if (baseOrgUsersResp != null && baseOrgUsersResp.isSuccess()) {
            return baseOrgUsersResp.getData();
        }
        return new ArrayList<>();
    }

    @Override
    public PageData<List<UserSearchDTO>> getUserPaging(UserPageDTO userPageDTO) {
        String body = JSON.toJSONString(userPageDTO);
        BaseUserPagingResp baseUserPagingResp = null;
        try {
            String response = HttpUtil.doPostJson(host, BaseServiceApi.USER_PAGING, null, null, body);
            baseUserPagingResp = JSON.parseObject(response, BaseUserPagingResp.class);
        } catch (Exception e) {
            LOGGER.error("getUserPaging exception", e);
        }

        if (baseUserPagingResp != null && baseUserPagingResp.isSuccess()) {
            return baseUserPagingResp.getData();
        }
        return null;
    }

    @Override
    public UserDetailDTO getUserDetail(String externalId) {
        Map<String, String> querys = new HashMap<>();
        querys.put("externalId", externalId);
        BaseUserDetailResp baseUserDetailResp = null;
        try {
            String response = HttpUtil.doGet(host, BaseServiceApi.USER_DETAIL, null, querys);
            baseUserDetailResp = JSON.parseObject(response, BaseUserDetailResp.class);
        } catch (Exception e) {
            LOGGER.error("getUserDetail exception", e);
        }

        if (baseUserDetailResp != null && baseUserDetailResp.isSuccess()) {
            return baseUserDetailResp.getData();
        }
        return null;
    }

    @Override
    public ResultJson<List<UserAuthAppResp>> listUserApp(String purchaseId) {
        UserAuthAppReq req = new UserAuthAppReq();
        if (StringUtils.isEmpty(purchaseId)) {
            purchaseId = appId;
        }
        req.setAppId(purchaseId);
        req.setExternalId(SessionContext.getIdaasUser().getUsername());
        try {
            log.info("listUserApp={}", req);
            String response = HttpUtil.doPostJson(host, BaseServiceApi.USER_APPS, null, null, JSON.toJSONString(req));
            log.info("listUserApp ret={}", response);
            ResultJson ret = JSON.parseObject(response, ResultJson.class);
            return ret;
        } catch (Exception e) {
            LOGGER.error("listUserApp exception", e);
        }
        return ResultJson.fail("平台基座接口调用异常");
    }

    @Override
    public ResultJson<List<MenuDTO>> listUserMenu(String purchaseId) {
        UserAuthAppReq req = new UserAuthAppReq();
        if (StringUtils.isEmpty(purchaseId)) {
            purchaseId = appId;
        }
        req.setAppId(purchaseId);
        req.setExternalId(SessionContext.getIdaasUser().getUsername());
        try {
            log.info("listUserMenu={}", req);
            String response = HttpUtil.doPostJson(host, BaseServiceApi.USER_MENUS, null, null, JSON.toJSONString(req));
            log.info("listUserMenu ret={}", response);
            ResultJson ret = JSON.parseObject(response, ResultJson.class);
            return ret;
        } catch (Exception e) {
            LOGGER.error("listUserApp exception", e);
        }
        return ResultJson.fail("平台基座接口调用异常");
    }

    @Override
    public ResultJson<AppSDKDetailResp> appDetail(String purchaseId) {
        try {
            log.info("appDetail={}", purchaseId);
            Map<String, String> querys = new HashMap<>();
            querys.put("appId", purchaseId);
            String response = HttpUtil.doGet(host, BaseServiceApi.APP_DETAIL, null, querys);
            log.info("appDetail ret={}", response);
            ResultJson ret = JSON.parseObject(response, ResultJson.class);
            return ret;
        } catch (Exception e) {
            LOGGER.error("appDetail exception", e);
        }
        return ResultJson.fail("平台基座接口调用异常");
    }
}
