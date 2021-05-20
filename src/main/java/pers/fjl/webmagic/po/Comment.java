package pers.fjl.webmagic.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 歌曲评论实体类
 */
@Data
@TableName(value = "Comment")
@Accessors(chain = true)
public class Comment {
    private Long commentId;
    private Long parentCommentId;
    private Integer likedCount;
    private String content;

    private String avatarUrl;
    private Long userId;
    private String nickname;

    private String songName;

    @TableField(exist = false)
    private User user;
}
