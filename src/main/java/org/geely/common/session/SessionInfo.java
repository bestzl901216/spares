package org.geely.common.session;

import lombok.Data;
import org.geely.common.model.IdaasUser;

@Data
public class SessionInfo {

    private String token;

    private IdaasUser idaasUser;

    public SessionInfo(String token, IdaasUser idaasUser) {
        this.token = token;
        this.idaasUser = idaasUser;
    }
}
