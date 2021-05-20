package pers.fjl.webmagic.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import pers.fjl.webmagic.po.User;

@Repository
public interface UserDao extends BaseMapper<User> {
}
