package org.geely.controller.common;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.geely.common.annotation.NoLogin;
import org.geely.common.model.ResultJson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wanbing.shi
 * @date 2023-09-05
 * @desc
 **/
@Api(value = "demo接口", tags = "demo接口")
@RestController
@NoLogin
public class DemoController {

    @ApiOperation("/hello")
    @GetMapping(value = "/hello")
    public String helloWorld(
    ) {
        return "true";
    }

    @ApiOperation("/v1/app/test")
    @GetMapping(value = "/v1/app/test")
    public ResultJson<String> test() {
        return ResultJson.success("{\"code\":\"200\",\"message\":\"操作成功\",\"data\":[{\"id\":1,\"name\":\"平台基座UAT\",\"applicationType\":1},{\"id\":3,\"name\":\"银河交易运营平台\",\"applicationType\":1},{\"id\":5,\"name\":\"（演示）前端组件\",\"applicationType\":2},{\"id\":7,\"name\":\"领克商城管理后台\",\"applicationType\":1},{\"id\":9,\"name\":\"整车商城\",\"applicationType\":2},{\"id\":15,\"name\":\"精品商城\",\"applicationType\":2},{\"id\":22,\"name\":\"OTA商城\",\"applicationType\":2},{\"id\":33,\"name\":\"活动营销平台\",\"applicationType\":1},{\"id\":34,\"name\":\"积分卡券\",\"applicationType\":2},{\"id\":35,\"name\":\"平台基座v1.2迭代测试\",\"applicationType\":2},{\"id\":38,\"name\":\"渠道域平台\",\"applicationType\":1},{\"id\":41,\"name\":\"智能分佣\",\"applicationType\":2},{\"id\":42,\"name\":\"销易通APP\",\"applicationType\":1},{\"id\":43,\"name\":\"测试平台应用001\",\"applicationType\":1},{\"id\":44,\"name\":\"测试微应用001\",\"applicationType\":2},{\"id\":45,\"name\":\"B端APP平台\",\"applicationType\":1},{\"id\":46,\"name\":\"泰山平台\",\"applicationType\":1},{\"id\":47,\"name\":\"盾山风控管理后台\",\"applicationType\":2},{\"id\":52,\"name\":\"销易通-test\",\"applicationType\":2},{\"id\":54,\"name\":\"线索域平台\",\"applicationType\":1},{\"id\":55,\"name\":\"线索中心\",\"applicationType\":2},{\"id\":56,\"name\":\"~用车体验中心Beta版~\",\"applicationType\":1},{\"id\":57,\"name\":\"试驾平台\",\"applicationType\":2},{\"id\":58,\"name\":\"用户触达B端中控台\",\"applicationType\":2},{\"id\":59,\"name\":\"合同中心测试环境\",\"applicationType\":1},{\"id\":60,\"name\":\"基座测试\",\"applicationType\":2},{\"id\":61,\"name\":\"OAuth2\",\"applicationType\":2},{\"id\":70,\"name\":\"用户触达C端中控台\",\"applicationType\":2},{\"id\":72,\"name\":\"试乘试驾数字管理平台-数字门店\",\"applicationType\":2},{\"id\":73,\"name\":\"试乘试驾数字管理平台-试驾平台\",\"applicationType\":2},{\"id\":74,\"name\":\"朱朝明分佣\",\"applicationType\":2}],\"success\":true}");
    }
}
