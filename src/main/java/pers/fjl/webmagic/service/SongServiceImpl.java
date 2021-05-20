package pers.fjl.webmagic.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.nd4j.shade.jackson.core.type.TypeReference;
import org.nd4j.shade.jackson.databind.DeserializationFeature;
import org.nd4j.shade.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pers.fjl.webmagic.dao.RisingListDao;
import pers.fjl.webmagic.dao.SongsDao;
import pers.fjl.webmagic.po.Artist;
import pers.fjl.webmagic.po.HotAlbums;
import pers.fjl.webmagic.po.RisingListPo;
import pers.fjl.webmagic.po.Songs;
import pers.fjl.webmagic.vo.SongsVo;
import pers.fjl.webmagic.po.*;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SongServiceImpl {
    @Resource
    private SongsDao songsDao;
    @Resource
    private RisingListDao risingListDao;

    /**
     * 获取飙升榜歌曲列表然后返回
     */
    public void getRisingList() throws Exception {
        List<RisingListPo> risingListPos = risingListDao.selectList(null);
        Integer i =0;
        for (RisingListPo risingListPo : risingListPos) {
            i++;
            if (i>=47){ //一次爬超过五十首会被封ip
                break;
            }
            Thread.sleep(300);  //休眠0.3秒
         crawlSongs(Long.parseLong(risingListPo.getSongId()));
        }
    }

    /**
     * 获取单个用户的详情信息
     * @param songId 歌曲id
     * @throws Exception
     */
    public void crawlSongs(Long songId) throws Exception {

        //        1.打开浏览器，创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //2.输入网址，发起get请求创建HttpGet对象
        HttpGet httpGet = new HttpGet("http://music.163.com/api/song/detail/?id="+songId+"&ids=%5B"+songId+"%5D");

        RequestConfig requestConfig = RequestConfig.custom()
//                .setProxy(proxy)
                .setConnectTimeout(10000)
                .setSocketTimeout(10000)
                .setConnectionRequestTimeout(3000)
                .build();
        httpGet.setConfig(requestConfig);
        //设置请求头消息
//        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
        //設置httpGet的头部參數信息

//        httpGet.setHeader("Accept", "Accept text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//
//        httpGet.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
//
//        httpGet.setHeader("Accept-Encoding", "gzip, deflate");
//
//        httpGet.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
//
//        httpGet.setHeader("Connection", "keep-alive");
//
//        httpGet.setHeader("Cookie", "__utma=226521935.73826752.1323672782.1325068020.1328770420.6;");
//
//        httpGet.setHeader("Host", "www.cnblogs.com");
//
//        httpGet.setHeader("refer", "http://www.baidu.com/s?tn=monline_5_dg&bs=httpclient4+MultiThreadedHttpConnectionManager");

        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
        //3.按回车发起请求，返回响应
        CloseableHttpResponse response = httpClient.execute(httpGet);

        //4.解析响应，获取数据
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity httpEntity = response.getEntity();
            String content = EntityUtils.toString(httpEntity, "utf-8");
            System.out.println(content);
            JSONObject jsonObject = JSONObject.parseObject(content);
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                try {
                    List<Songs> songs = mapper.readValue(jsonObject.get("songs").toString(), new TypeReference<List<Songs>>(){});
                    for (Songs song : songs) {
                        List<Artist> artists = song.getArtists();
                        Artist artist = artists.get(0); //获取第一位歌手即可
                        HotAlbums album = song.getAlbum();

                        SongsVo songsVo = new SongsVo();
                        songsVo.setSongId(song.getId());    //歌曲id
                        songsVo.setSongName(song.getName());    //歌名
                        songsVo.setArtist(artist.getName());  //艺人

                        songsVo.setSubType(album.getSubType()); //版本
                        songsVo.setName(album.getName());   //专辑名
                        songsVo.setType(album.getType());   //专辑类型
                        songsVo.setSize(album.getSize());   //专辑有多少首歌
                        songsVo.setCompany(album.getCompany()); //公司
                        songsVo.setPicUrl(album.getPicUrl());   //专辑图片
                        songsDao.insert(songsVo);
                    }
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
        }
    }

}
