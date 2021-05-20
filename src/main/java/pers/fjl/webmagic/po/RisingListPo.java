package pers.fjl.webmagic.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@TableName(value = "RisingList")
@Accessors(chain = true)
public class RisingListPo {

    private String songId;
    private String songName;
    private String artist;
    private String songUrl;
}
