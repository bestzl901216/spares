package org.geely.common.model;

import lombok.Data;

@Data
public class OrgDetailDTO {
    /**
     * organizationName : 电子生产管理
     * externalId : HOST-218558
     * parentExternalId : HOST-206699
     * type : DEPARTMENT
     * rootNode : false
     * sortNumber : 0
     * enabled : true
     * description : 通过SCIM同步组织机构
     * extendFields : {"dealerName":"","isCompany":"0"}
     * uuid : e30d3df587ec6444917a5b0031c6f7ecY3vzV7RF3XA
     * sourceType : null
     */

    private String organizationName;
    private String externalId;
    private String parentExternalId;
    private String type;
    private boolean rootNode;
    private int sortNumber;
    private boolean enabled;
    private String description;
    private ExtendFieldsBean extendFields;
    private String uuid;
    private Object sourceType;

    @Data
    public static class ExtendFieldsBean {
        private String dealerName;
        private String isCompany;
    }

}
