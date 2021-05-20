package pers.fjl.webmagic.main;

import pers.fjl.webmagic.processor.ArtistProcessor;
import us.codecraft.webmagic.Spider;

public class ArtistMain {
    public static void main(String[] args) {
        String startUrl="https://music.163.com/#/discover/artist/signed/";
        //启动爬虫
        Spider spider = Spider.create(new ArtistProcessor());
//        spider.addPipeline(new RisingListPipeline());
//        spider.setScheduler(new RedisScheduler("127.0.0.1"));
        spider.addUrl(startUrl);
        spider.start();
    }
}
