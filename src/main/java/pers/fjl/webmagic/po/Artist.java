package pers.fjl.webmagic.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@TableName(value = "Artist")
@Accessors(chain = true)
/**
 * 歌手实体类
 */
public class Artist {
    private Long id;
    private Integer musicSize;  //音乐个数
    private Integer albumSize;  //专辑个数
    private String picUrl;
    private String name;
}
