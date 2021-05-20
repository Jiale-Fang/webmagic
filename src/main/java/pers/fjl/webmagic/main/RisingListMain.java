package pers.fjl.webmagic.main;

import pers.fjl.webmagic.pipeline.RisingListPipeline;
import pers.fjl.webmagic.processor.RisingListProcessor;
import us.codecraft.webmagic.Spider;

public class RisingListMain {
    public static void main(String[] args) {
        String startUrl="https://music.163.com/#/discover/toplist?id=19723756";//起始歌单的url
        //启动爬虫
        Spider spider = Spider.create(new RisingListProcessor());
        spider.addPipeline(new RisingListPipeline());
//        spider.setScheduler(new RedisScheduler("127.0.0.1"));
        spider.addUrl(startUrl);
        spider.start();
    }
}
