package org.geely.service.impl;

import com.alibaba.fastjson2.JSON;
import org.geely.common.constants.BaseServiceApi;
import org.geely.common.model.*;
import org.geely.common.utils.HttpUtil;
import org.geely.service.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    @Value("${baseService.host}")
    private String host;

    @Override
    public OrgDetailDTO getOrgDetail(String externalId) {
        Map<String, String> querys = new HashMap<>();
        querys.put("externalId", externalId);
        BaseOrgDetailResp baseOrgDetailResp = null;
        try {
            String response = HttpUtil.doGet(host, BaseServiceApi.ORG_DETAIL, null, querys);
            baseOrgDetailResp = JSON.parseObject(response, BaseOrgDetailResp.class);
        } catch (Exception e) {
            LOGGER.error("getRootOrgDetail exception", e);
        }

        if (baseOrgDetailResp != null && baseOrgDetailResp.isSuccess()) {
            return baseOrgDetailResp.getData();
        }
        return null;
    }

    @Override
    public List<OrgChildDTO> getChildOrg(String ouUuid) {
        Map<String, String> querys = new HashMap<>();
        if (StringUtils.hasText(ouUuid)){
            querys.put("ouUuid", ouUuid);
        }
        BaseOrgsResp baseOrgsResp = null;
        try {
            String response = HttpUtil.doGet(host, BaseServiceApi.CHILD_ORG, null, querys);
            baseOrgsResp = JSON.parseObject(response, BaseOrgsResp.class);
        } catch (Exception e) {
            LOGGER.error("getChildOrg exception", e);
        }

        if (baseOrgsResp!=null && baseOrgsResp.isSuccess()){
            return baseOrgsResp.getData();
        }
        return new ArrayList<>();
    }

}
