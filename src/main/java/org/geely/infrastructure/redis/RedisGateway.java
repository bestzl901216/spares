package org.geely.infrastructure.redis;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import org.geely.common.exception.BizException;
import org.geely.domain.common.data.SessionData;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author ricardo zhou
 */
@Component
public class RedisGateway {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 向redis中保存sessionData
     *
     * @param sessionData sessionData
     * @return token
     */
    public String saveSessionData(SessionData sessionData) {
        String token = IdUtil.simpleUUID();
        return this.saveSessionData(token, sessionData);
    }

    /**
     * 向redis中保存sessionData
     *
     * @param token       token
     * @param sessionData sessionData
     * @return token
     */
    public String saveSessionData(String token, SessionData sessionData) {
        Assert.notBlank(token, "token不存在");
        Assert.notNull(sessionData, "sessionData不存在");
        Assert.notNull(sessionData.getAccountData(), "accountData不存在");
        Assert.notNull(sessionData.getAccountData().getPhone(), "用户名不存在");
        //单点登录，删除老token
        String userNameKey = userNameKey(sessionData.getAccountData().getPhone());
        String oldToken = stringRedisTemplate.opsForValue().get(userNameKey);
        removeSessionData(oldToken);
        //第二天3点过期
        LocalDateTime expireAt = LocalDateTime.now().plusDays(1).toLocalDate().atTime(3, 0);
        Duration duration = Duration.between(LocalDateTime.now(), expireAt);
        stringRedisTemplate.opsForValue().set(tokenKey(token), JSONUtil.toJsonStr(sessionData), duration);
        stringRedisTemplate.opsForValue().set(userNameKey, token, duration);
        return token;
    }


    /**
     * 根据token查询sessionData
     *
     * @param token token
     * @return sessionData
     */
    public Optional<SessionData> querySessionData(String token) {
        if (CharSequenceUtil.isBlank(token)) {
            return Optional.empty();
        }
        String redisKey = tokenKey(token);
        String redisValue = stringRedisTemplate.opsForValue().get(redisKey);
        if (redisValue != null) {
            return Optional.of(JSONUtil.toBean(redisValue, SessionData.class));
        } else {
            return Optional.empty();
        }
    }

    /**
     * 根据token查询sessionData
     *
     * @param token token
     * @return sessionData
     */
    public SessionData getSessionData(String token) {
        return querySessionData(token).orElseThrow(() -> new BizException("session不存在", token));
    }

    /**
     * 刷新sessionData
     *
     * @param token          token
     * @param newSessionData newSessionData
     */
    public void refreshSessionData(String token, SessionData newSessionData) {
        SessionData sessionData = this.querySessionData(token).orElse(null);
        if (sessionData == null) {
            return;
        }
        BeanUtil.fillBeanWithMap(BeanUtil.beanToMap(newSessionData), sessionData, CopyOptions.create(SessionData.class, true, "accountId"));
        stringRedisTemplate.opsForValue().set(tokenKey(token), JSONUtil.toJsonStr(sessionData), 1, TimeUnit.HOURS);
    }

    /**
     * 删除token
     *
     * @param token token
     */
    public void removeSessionData(String token) {
        if (CharSequenceUtil.isNotBlank(token)) {
            stringRedisTemplate.delete(tokenKey(token));
        }
    }

    /**
     * 保存手机验证码
     * 1分钟有效
     *
     * @param phone 手机号
     * @param code  验证码
     */
    public void savePhoneCode(String phone, String code) {
        stringRedisTemplate.opsForValue().set(phone, code, 1, TimeUnit.MINUTES);
    }

    /**
     * 获取手机验证码
     *
     * @param phone 手机号
     * @return 验证码
     */
    public String getPhoneCode(String phone) {
        return stringRedisTemplate.opsForValue().get(phone);
    }

    /**
     * 获取并删除手机验证码
     *
     * @param phone 手机号
     * @return 验证码
     */
    public String getAndDeletePhoneCode(String phone) {
        return stringRedisTemplate.opsForValue().getAndDelete(phone);
    }



    private static String tokenKey(String token) {
        return "token:" + token;
    }
    private static String userNameKey(String phone) {
        return "phone:" + phone;
    }
}
