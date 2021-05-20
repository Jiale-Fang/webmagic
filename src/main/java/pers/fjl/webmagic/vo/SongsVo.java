package pers.fjl.webmagic.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@TableName(value = "songs")
@Accessors(chain = true)
public class SongsVo {
    private Long songId;
    private String songName;
    private String artist;

    private String subType; //版本
    private String name;    //专辑名
    private String type;    //专辑类型
    private Integer size;   //专辑有多少首歌
    private String company; //公司
    private String picUrl;  //专辑图片
}
