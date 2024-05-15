package org.geely.common.model;

import lombok.Data;

import java.util.List;

@Data
public class UserDetailDTO {

    private String externalId;
    private String username;
    private String displayName;
    private String phoneNumber;
    private String email;
    private boolean locked;
    private boolean enabled;
    private String description;
    private ExtendFieldsBean extendFields;
    private Object avatarUuid;
    private String uuid;
    private List<String> belongs;

    @Data
    public static class ExtendFieldsBean {
        private String POSITION_NBR;
        private String POSITION_DESCR;
        private String position_name;
        private String positionCode;
        private String position;
        private String isAdmin;
        private String supervisorId;
        private String userid;
        private String POSITION_TYPE;
    }
}
