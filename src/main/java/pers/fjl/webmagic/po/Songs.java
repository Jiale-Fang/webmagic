package pers.fjl.webmagic.po;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
/**
 * 歌曲实体类
 */
public class Songs {
//    private Long songId;
//    private String songName;
//    private String artist;
    private String name;    //歌名
    private Long id;

//    private String subType; //版本
//    private String albumName;    //专辑名
//    private String type;    //专辑类型
//    private Integer size;   //专辑有多少首歌
//    private String company; //公司
//    private String picUrl;  //专辑图片

    @TableField(exist = false)
    private List<Artist> artists;

    @TableField(exist = false)
    private HotAlbums album;
}
