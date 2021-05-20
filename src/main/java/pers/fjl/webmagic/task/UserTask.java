package pers.fjl.webmagic.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pers.fjl.webmagic.service.UserServiceImpl;
import us.codecraft.webmagic.scheduler.RedisScheduler;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class UserTask {

    @Resource
    private UserServiceImpl userServiceAuto;

    private static UserServiceImpl userService;

    @PostConstruct
    public void init() {
        userService = this.userServiceAuto;  //将注入的对象交给静态对象管理
    }

    @Resource
    private RedisScheduler redisScheduler;

    @Scheduled(cron = "59 21 15 * * ?")
    public void userTask() throws Exception {
        System.out.println("爬取用户详细信息");
        userService.getUserList("0","40"); //获取comment列表里的第几位到第几位的用户，  最好不要超过五十个，也要在comment表的范围内
    }

}
