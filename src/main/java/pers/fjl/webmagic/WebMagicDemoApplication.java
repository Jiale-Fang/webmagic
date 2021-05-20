package pers.fjl.webmagic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import us.codecraft.webmagic.scheduler.RedisScheduler;

@SpringBootApplication
@EnableScheduling
@MapperScan("pers.fjl.webmagic.dao")
public class WebMagicDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebMagicDemoApplication.class,args);
    }

    @Bean
    public RedisScheduler redisScheduler() {
        return new RedisScheduler("127.0.0.1");
    }
}
