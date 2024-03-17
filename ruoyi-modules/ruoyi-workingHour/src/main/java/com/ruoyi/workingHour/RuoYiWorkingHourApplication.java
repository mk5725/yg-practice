package com.ruoyi.workingHour;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

/**
 * 工时模块
 *
 * @author chenj
 */
@EnableDubbo
@SpringBootApplication
public class RuoYiWorkingHourApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(RuoYiWorkingHourApplication.class);
        application.setApplicationStartup(new BufferingApplicationStartup(2048));
        application.run(args);
        System.out.println("(♥◠‿◠)ﾉﾞ  工时模块启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}
