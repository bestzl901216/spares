package org.geely.controller;

import cn.hutool.extra.spring.SpringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.geely.common.annotation.Permission;
import org.geely.common.model.ResultJson;
import org.geely.domain.common.data.SessionData;
import org.geely.infrastructure.redis.RedisGateway;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author ricardo zhou
 */
@Api(value = "应用接口", tags = "应用接口")
@RestController
@RequestMapping("/app")
public class AppController {

    @Resource
    private RedisGateway redisGateway;
    @Resource
    private RocketMQTemplate rocketMqTemplate;

    @Permission("helloWorld")
    @ApiOperation("心跳检测")
    @GetMapping(value = "/helloWorld")
    public ResultJson<String> helloWorld() {
        return ResultJson.success("Hello, welcome to mall world!");
    }

    @ApiOperation("创建session数据")
    @PostMapping("/session")
    public ResultJson<String> saveSession(@RequestParam String token, @RequestBody SessionData sessionData) {
        redisGateway.saveSessionData(token, sessionData);
        return ResultJson.success(token);
    }

    @ApiOperation("查询session数据")
    @GetMapping("/session")
    public ResultJson<String> getSession(@RequestParam String token) {
        return ResultJson.success(redisGateway.getSessionData(token).getAccountData().getName());
    }

    @ApiOperation("发送消息")
    @PostMapping(value = "/message")
    public ResultJson<String> message(@RequestParam String tag, @RequestBody String message) {
        SendResult sendResult = rocketMqTemplate.syncSend("test:" + tag, message);
        return ResultJson.success(sendResult.getMsgId());
    }

    @ApiOperation("测试方法")
    @GetMapping("/test")
    public ResultJson<String> test() {
        return ResultJson.success("success");
    }

    @ApiOperation("当前环境")
    @GetMapping("/env")
    public ResultJson<String> env() {
        String profile = SpringUtil.getActiveProfile();
        return ResultJson.success(profile);
    }

}

