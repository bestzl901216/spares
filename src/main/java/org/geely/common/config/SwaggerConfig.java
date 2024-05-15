package org.geely.common.config;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import org.geely.common.constants.MvcConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @author wanbing.shi
 * @date 2023-09-14
 * @desc swagger地址:http://localhost:8090/api/doc.html
 * swagger JSON地址: http://localhost:8090/api/v2/api-docs?group=吉利营销数字中心
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket(Environment environment) {
        //设置要显示的swagger环境
        Profiles profiles = Profiles.of("dev", "uat");
        boolean flag = environment.acceptsProfiles(profiles);
        RequestParameter idassToken = new RequestParameterBuilder().name("Idass_token").description(" token 信息 ").in(ParameterType.HEADER).required(false).build();
        RequestParameter platformType = new RequestParameterBuilder().name(MvcConstant.PLATFORM_HEADER).description("平台类型").in(ParameterType.HEADER).required(false).build();
        RequestParameter platformId = new RequestParameterBuilder().name(MvcConstant.PLATFORM_ID_HEADER).description("平台id").in(ParameterType.HEADER).required(false).build();
        RequestParameter mallId = new RequestParameterBuilder().name(MvcConstant.MALL_ID).description("商城id").in(ParameterType.HEADER).required(false).build();
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("吉利营销数字中心")
                .apiInfo(apiInfo())
                .enable(flag)
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 增加header
                .build().globalRequestParameters(Lists.newArrayList(idassToken, platformType, platformId, mallId));
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("", "", "");
        return new ApiInfo(
                "SwaggerAPI文档",
                "吉利营销数字中心",
                "v1.0",
                "",
                contact,
                "Apache Tomcat/9.0.64",
                "https://tomcat.apache.org/tomcat-9.0-doc/building.html",
                new ArrayList<>()
        );
    }

}
