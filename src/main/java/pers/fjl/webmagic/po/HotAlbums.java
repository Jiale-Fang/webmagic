package pers.fjl.webmagic.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@TableName(value = "HotAlbums")
@Accessors(chain = true)
public class HotAlbums {
    private Long id;
    private Integer mark;   //点赞数
    private Long publishTime;
    private String company; //公司
    private String picUrl;  //专辑图片
    private String description;
    private String subType; //版本
    private String name;    //专辑名
    private String type;    //专辑类型
    private Integer size;   //专辑有多少首歌
    private Long artistId;
    private String artistName;
    @TableField(exist = false)
    private Artist artist;
}
