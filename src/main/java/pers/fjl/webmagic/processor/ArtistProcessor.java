package pers.fjl.webmagic.processor;

//import org.junit.Test;
import lombok.SneakyThrows;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;
import pers.fjl.webmagic.service.AlbumServiceImpl;
import pers.fjl.webmagic.service.ArtistServiceImpl;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 爬取入驻歌手
 */
@Component
public class ArtistProcessor implements PageProcessor {

    @Resource
    private ArtistServiceImpl artistServiceAuto;
    @Resource
    private AlbumServiceImpl albumServiceAuto;

    private static ArtistServiceImpl artistService;
    private static AlbumServiceImpl albumService;

    @PostConstruct
    public void init() {
        artistService = this.artistServiceAuto;  //将注入的对象交给静态对象管理
        albumService = this.albumServiceAuto;
    }

    /**
     * 实现WebMagic框架的process方法
     * 每次解析队列中的请求都会调用该方法
     * @param page
     */
    @SneakyThrows
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
        这里设置等待时长为7秒
        超过这个时间就会抛出异常
         */
        WebDriverWait wait = new WebDriverWait(driver, 7);
        //等待存放歌曲的table节点出现
        WebElement table = wait.until(ExpectedConditions.
                presenceOfElementLocated(By.xpath("//ul[@id='m-artist-box']")));
        //获取所有歌曲所在的tr节点
        List<WebElement> lis = table.findElements(By.xpath("//li"));
        System.out.println("共有"+lis.size()+"个艺人");
        int i=1;
        List<String> artistUrl = new ArrayList<>();
        for (WebElement li : lis) {
            try {
                //获取艺人的id
                WebElement href = wait.until(ExpectedConditions.
                        presenceOfElementLocated(By.xpath("/html/body/div[3]/div[2]/div/div/div[2]/ul/li["+ i++ +"]/p/a[1]")));
                String artistId = href.getAttribute("href").split("=")[1];
                artistService.crawlArtist(Long.parseLong(artistId));
                albumService.crawlAlbum(Long.parseLong(artistId),"5");
            }catch (NoSuchElementException | TimeoutException e1){
                e1.printStackTrace();
            }

        }

    }
    /*
    对爬虫进行配置
    设置超时时间和编码方式
    */
    private final Site site=Site.me().setTimeOut(5000).setCharset("utf8").setSleepTime(100);

    public Site getSite() {
        return site;
    }

}
