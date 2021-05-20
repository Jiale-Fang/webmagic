package pers.fjl.webmagic.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import pers.fjl.webmagic.po.Comment;

@Repository
public interface CommentDao  extends BaseMapper<Comment> {
}
