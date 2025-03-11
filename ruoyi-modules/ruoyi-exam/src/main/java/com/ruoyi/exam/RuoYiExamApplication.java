package com.ruoyi.exam;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

/**
 * 工时模块
 *
 */
@EnableDubbo
@SpringBootApplication
public class RuoYiExamApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(RuoYiExamApplication.class);
        application.setApplicationStartup(new BufferingApplicationStartup(2048));
        application.run(args);
        System.out.println("(♥◠‿◠)ﾉﾞ  考试模块启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}
