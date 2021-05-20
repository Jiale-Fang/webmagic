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
import pers.fjl.webmagic.dao.AlbumDao;
import pers.fjl.webmagic.po.HotAlbums;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Service
public class AlbumServiceImpl {
    @Resource
    private AlbumDao albumDao;

    /**
     * 获取歌手专辑信息
     *
     * @param artistId 歌手id
     * @param limit    歌手的前多少个专辑
     * @throws Exception
     */
    public void crawlAlbum(Long artistId, String limit) {
        //        1.打开浏览器，创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //2.输入网址，发起get请求创建HttpGet对象
        HttpGet httpGet = new HttpGet("http://music.163.com/api/artist/albums/" + artistId + "?id=" + artistId + "&offset=0&total=true&limit=" + limit);

        RequestConfig requestConfig = RequestConfig.custom()
//                .setProxy(proxy)
                .setConnectTimeout(10000)
                .setSocketTimeout(10000)
                .setConnectionRequestTimeout(3000)
                .build();
        httpGet.setConfig(requestConfig);
        //设置请求头消息
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");

        try {
            //3.按回车发起请求，返回响应
            CloseableHttpResponse response = httpClient.execute(httpGet);

            //4.解析响应，获取数据
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = response.getEntity();
                String content = EntityUtils.toString(httpEntity, "utf-8");
                JSONObject jsonObject = JSONObject.parseObject(content);
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                List<HotAlbums> hotAlbumsList = mapper.readValue(jsonObject.get("hotAlbums").toString(), new TypeReference<List<HotAlbums>>() {
                });
                for (HotAlbums hotAlbums : hotAlbumsList) {
                    hotAlbums.setArtistName(hotAlbums.getArtist().getName()).setArtistId(hotAlbums.getArtist().getId());
                    try {
                        albumDao.insert(hotAlbums);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        } catch ( IOException e2) {
            e2.printStackTrace();
        }

    }
}
