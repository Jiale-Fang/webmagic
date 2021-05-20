package pers.fjl.webmagic.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.nd4j.shade.jackson.databind.DeserializationFeature;
import org.nd4j.shade.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pers.fjl.webmagic.dao.CommentDao;
import pers.fjl.webmagic.po.Comment;
import pers.fjl.webmagic.po.User;
import pers.fjl.webmagic.dao.UserDao;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl {
    @Resource
    private UserDao userDao;
    @Resource
    private CommentDao commentDao;

    /**
     * 获取单个用户的详情信息
     * @param userId
     * @throws Exception
     */
    public void crawlUser(Long userId) throws Exception {
        //        1.打开浏览器，创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //2.输入网址，发起get请求创建HttpGet对象
        HttpGet httpGet = new HttpGet("https://music.163.com/api/v1/user/detail/" + userId);

        RequestConfig requestConfig = RequestConfig.custom()
//                .setProxy(proxy)
                .setConnectTimeout(10000)
                .setSocketTimeout(10000)
                .setConnectionRequestTimeout(3000)
                .build();
        httpGet.setConfig(requestConfig);
        //设置请求头消息
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");

        //3.按回车发起请求，返回响应
        CloseableHttpResponse response = httpClient.execute(httpGet);

        //4.解析响应，获取数据
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity httpEntity = response.getEntity();
            String content = EntityUtils.toString(httpEntity, "utf-8");
            JSONObject jsonObject = JSONObject.parseObject(content);
            Integer listenSongs= (Integer) jsonObject.get("listenSongs");
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            User user = mapper.readValue(jsonObject.get("profile").toString(), User.class);
            user.setListenSongs(listenSongs);
            userDao.insert(user);
        }
    }

    public void getUserList(String start, String end) throws Exception {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.select("DISTINCT user_id").last("limit "+start+"," + end);
        List<Comment> commentList = commentDao.selectList(wrapper);//获取含有了用户id的comment
        for (Comment comment : commentList) {
            crawlUser(comment.getUserId());
        }
    }
}
