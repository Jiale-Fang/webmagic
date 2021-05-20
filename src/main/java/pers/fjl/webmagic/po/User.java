package pers.fjl.webmagic.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@TableName(value = "User")
@Accessors(chain = true)
public class User {
    private Long userId;
    private String avatarUrl;

    private String nickname;

    private Long birthday;
    private Integer gender;

    private Long createTime;

    private String signature; //自我简介

    private Integer followeds;  //粉丝
    private Integer follows;    //关注
    private Integer eventCount;     //动态
    private Integer listenSongs;    //听歌次数
}
