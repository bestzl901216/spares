package org.geely.infrastructure.sms;

import org.springframework.stereotype.Component;

/**
 * @author ricardo zhou
 */
@Component
public class SmsGateway {
    /**
     * 获取手机验证码
     *
     * @param phone 手机号
     * @return 验证码
     */
    public String sendSmsCode(String phone) {
        return "123456";
    }
}
