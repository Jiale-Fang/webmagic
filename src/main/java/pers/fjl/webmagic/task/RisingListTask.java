package pers.fjl.webmagic.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pers.fjl.webmagic.pipeline.RisingListPipeline;
import pers.fjl.webmagic.processor.RisingListProcessor;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.RedisScheduler;

import javax.annotation.Resource;

@Component
public class RisingListTask {

    @Resource
    private RisingListProcessor risingListProcessor;

    @Resource
    private RisingListPipeline risingListPipeline;

    @Resource
    private RedisScheduler redisScheduler;

    @Scheduled(cron = "59 13 15 * * ?")
    public void javaTask() {
        System.out.println("爬取飙升榜");
        Spider spider = Spider.create(risingListProcessor);
        spider.addUrl("https://music.163.com/#/discover/toplist?id=19723756");
        spider.addPipeline(risingListPipeline);
//        spider.setScheduler(redisScheduler);
        spider.start();
    }

}
