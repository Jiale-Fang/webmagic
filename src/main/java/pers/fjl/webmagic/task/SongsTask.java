package pers.fjl.webmagic.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pers.fjl.webmagic.service.SongServiceImpl;
import us.codecraft.webmagic.scheduler.RedisScheduler;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class SongsTask {

    @Resource
    private SongServiceImpl songServiceAuto;

    private static SongServiceImpl songService;

    @PostConstruct
    public void init() {
        songService = this.songServiceAuto;  //将注入的对象交给静态对象管理
    }

    @Resource
    private RedisScheduler redisScheduler;

    @Scheduled(cron = "59 43 14 * * ?")
    public void userTask() throws Exception {
        System.out.println("爬取歌曲详细信息");
        songService.getRisingList();
    }

}
