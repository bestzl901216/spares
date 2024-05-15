package org.geely.common.model;

import lombok.Data;

@Data
public class LoginResp {

    private String accessToken;

    private IdaasUser idaasUser;

}
