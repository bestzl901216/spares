package org.geely.domain.common;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import org.apache.commons.lang3.StringUtils;
import org.geely.domain.common.data.LoginFailedData;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;


/**
 * 登录失败
 *
 * @author cong huang
 */
public class LoginFailed {
    private LoginFailedData data;
    private String userName;
    private final int limitMinutes = 5;
    private final int limitCount = 5;
    private final String errorMsgTemplate = "连续登录失败次数太多，请{}分钟后再试！";

    private LoginFailed(String userName, LoginFailedData data) {
        this.data = ObjectUtil.clone(data);
        this.userName = userName;
    }

    public static LoginFailed instanceOfAndCheck(String userName) {
        Assert.notBlank(userName, "用户名异常");
        String redisKey = getKey(userName);
        StringRedisTemplate stringRedisTemplate = SpringUtil.getBean(StringRedisTemplate.class);
        String redisValue = stringRedisTemplate.opsForValue().get(redisKey);
        if (redisValue != null) {
            LoginFailed instance = new LoginFailed(userName, JSONUtil.toBean(redisValue, LoginFailedData.class));
            instance.check();
            return instance;
        } else {
            return new LoginFailed(userName, new LoginFailedData());
        }
    }

    public void delete() {
        if (StringUtils.isBlank(userName)) {
            return;
        }
        if(data == null || data.getCount() == null || data.getCount() == 0) {
            return;
        }
        String redisKey = getKey(userName);
        StringRedisTemplate stringRedisTemplate = SpringUtil.getBean(StringRedisTemplate.class);
        stringRedisTemplate.delete(redisKey);
        this.userName = null;
        this.data = null;
    }

    public void increase() {
        Assert.notNull(data);
        Assert.notBlank(userName);
        data.setCount(data.getCount() + 1);
        data.setLastTime(LocalDateTime.now());
        String redisKey = getKey(userName);
        StringRedisTemplate stringRedisTemplate = SpringUtil.getBean(StringRedisTemplate.class);
        stringRedisTemplate.opsForValue().set(redisKey, JSONUtil.toJsonStr(data),  24, TimeUnit.HOURS);

        Assert.isTrue(data.getCount() < limitCount, errorMsgTemplate, limitMinutes);
    }

    private void check() {
        if(data.getCount() < limitCount){
            return;
        }
        Duration duration = Duration.between(data.getLastTime(), LocalDateTime.now());
        if (duration.getSeconds() >=  limitMinutes * 60) {
            return;
        }
        long diffInMinutes = limitMinutes - duration.toMinutes();
        Assert.isTrue(false, errorMsgTemplate, diffInMinutes);
    }

    private static String getKey(String phone) {
        return "login_failed:" + phone;
    }
}
