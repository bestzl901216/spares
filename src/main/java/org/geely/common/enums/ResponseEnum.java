package org.geely.common.enums;

public enum ResponseEnum {

    USER_NOT_LOGIN("user-not-login", "用户未登录"),

    USER_TOKEN_EXPIRED ("user-token-expired", "用户token已失效"),

    LOGIN_FAIL ("login_fail", "登录失败");

    private final String code;

    private final String message;

    ResponseEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage(){
        return message;
    }
}
