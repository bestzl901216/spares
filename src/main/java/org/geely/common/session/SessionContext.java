package org.geely.common.session;

import org.geely.common.model.IdaasUser;

/**
 * 会话上下文信息
 *
 * @author yancheng.guo
 */
public class SessionContext {

    private SessionContext() {
    }

    private static ThreadLocal<SessionInfo> sessionLocal = new ThreadLocal<>();

    public static void setSessionInfo(SessionInfo sessionInfo) {
        sessionLocal.set(sessionInfo);
    }

    /**
     * 获取会话信息
     *
     * @return
     */
    public static SessionInfo getSessionInfo() {
        return sessionLocal.get();
    }

    public static void removeSessionInfo() {
        sessionLocal.remove();
    }

    /**
     * 获取当前登录用户
     *
     * @return
     */
    public static IdaasUser getIdaasUser() {
        SessionInfo sessionInfo = getSessionInfo();
        if (null != sessionInfo) {
            return sessionInfo.getIdaasUser();
        }
        return null;
    }
}
