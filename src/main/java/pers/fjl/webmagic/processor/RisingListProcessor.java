package pers.fjl.webmagic.processor;

//import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;
import pers.fjl.webmagic.po.RisingListPo;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * 对应飙升榜的一百首歌
 */
@Component
public class RisingListProcessor implements PageProcessor {

    /**
     * 实现WebMagic框架的process方法
     * 每次解析队列中的请求都会调用该方法
     * @param page
     */
    public void process(Page page) {
        //创建谷歌浏览器驱动
        System.getProperties().setProperty("webdriver.chrome.driver", "C:\\Users\\Administrator.DESKTOP-2SITVO1\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //进入页面
        driver.get(page.getUrl().toString());
        //*****找到歌单列表所在的框架
        WebElement iframelement = driver.findElement(By.xpath("//iframe[@id='g_iframe']"));
        //*****进入框架
        driver.switchTo().frame(iframelement);
        /*
        实际场景中可能会发生页面节点没有加载出来但是程序已经跑完的情况
        可以利用显示等待，等待节点加载出来程序继续运行
        但是不能无限等待
        这里设置等待时长为5秒
        超过这个时间就会抛出异常
         */
        WebDriverWait wait = new WebDriverWait(driver, 5);
        //等待存放歌曲的table节点出现
        WebElement table = wait.until(ExpectedConditions.
                presenceOfElementLocated(By.xpath("//table[@class='m-table m-table-rank']")));
        //获取所有歌曲所在的tr节点
        List<WebElement> trs = table.findElements(By.xpath("//tbody/tr"));
        List<RisingListPo> risingListPos = new ArrayList<>();
        System.out.println("歌单共有歌曲"+trs.size()+"首");
        for (WebElement tr : trs) {
            RisingListPo risingListPo = new RisingListPo();
            //获取歌曲id
            String songId = tr.findElement(By.xpath(".//span[@class='txt']/a"))
                    .getAttribute("href").split("=")[1];
            //获取歌曲name
            String songName = tr.findElement(By.xpath(".//span[@class='txt']/a/b"))
                    .getAttribute("title").replace("\n", "");
            //获取歌手
            String artist = tr.findElement(By.xpath(".//div[@class='text']"))
                    .getAttribute("title").replace("\n", "");
            //获取时长

            System.out.println(songId);
            System.out.println(songName);
            System.out.println(artist);
            //拼接外链地址
            String realUrl="https://link.hhtjim.com/163/" + songId + ".mp3";    //歌曲url
            risingListPo.setSongId(songId).setSongName(songName).setArtist(artist).setSongUrl(realUrl);
            risingListPos.add(risingListPo);
        }
        page.putField("risingLists", risingListPos);
    }
    /*
    对爬虫进行配置
    设置超时时间和编码方式
    */
    private Site site=Site.me().setTimeOut(5000).setCharset("utf8").setSleepTime(100);

    public Site getSite() {
        return site;
    }

}
