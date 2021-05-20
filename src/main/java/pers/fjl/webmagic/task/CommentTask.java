package pers.fjl.webmagic.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pers.fjl.webmagic.service.CommentServiceImpl;

import javax.annotation.Resource;

@Component
public class CommentTask {

    @Resource
    private CommentServiceImpl commentService;

    @Scheduled(cron = "59 16 15 * * ?")
    public void commentTask() throws Exception {
        commentService.getComments("10","100");   //爬取飙升榜歌曲的数量，以及评论数        歌曲数要小于45
    }
}
