package org.geely;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author Wanbing.Shi
 * @date 2023-09-04
 * @desc 启动类
 */

@Slf4j
@SpringBootApplication
@EnableScheduling
@Configuration
@EnableOpenApi
public class GpbArchetypeDemoApplication {
    public static void main(String[] args) {

        SpringApplication.run(GpbArchetypeDemoApplication.class, args);

        log.info("=========== GpbArchetypeDemoApplication启动成功 ===========");
    }
}