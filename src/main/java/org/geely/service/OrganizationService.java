package org.geely.service;

import org.geely.common.model.OrgChildDTO;
import org.geely.common.model.OrgDetailDTO;

import java.util.List;

public interface OrganizationService {

    /**
     * 查询组织详情
     * @return
     */
    OrgDetailDTO getOrgDetail(String externalId);

    /**
     * 查询子组织
     * @param ouUuid
     * @return
     */
    List<OrgChildDTO> getChildOrg(String ouUuid);

}
