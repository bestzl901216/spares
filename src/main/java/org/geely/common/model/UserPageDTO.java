package org.geely.common.model;

import lombok.Data;

@Data
public class UserPageDTO {
    private String phoneNumber;
    private String email;
    private Integer status;
    private String orgId;
    private String orgName;
    private String userName;
    private Boolean type = Boolean.TRUE;
    private String roleName;
    private String psid;
    private Integer current;
    private Integer size;
    private String displayName;
}
