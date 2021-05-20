package pers.fjl.webmagic.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pers.fjl.webmagic.processor.ArtistProcessor;
import us.codecraft.webmagic.Spider;

import javax.annotation.Resource;

@Component
public class ArtistTask {

    @Resource
    private ArtistProcessor artistProcessor;

    @Scheduled(cron = "59 27 15 * * ?")
    public void artisTask() throws Exception {
        System.out.println("爬取歌手详细信息以及歌手的专辑信息");
        Spider spider = Spider.create(artistProcessor);
        spider.addUrl("https://music.163.com/#/discover/artist/signed/");
        spider.start();
    }

}
