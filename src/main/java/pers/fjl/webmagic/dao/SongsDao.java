package pers.fjl.webmagic.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import pers.fjl.webmagic.vo.SongsVo;

@Repository
public interface SongsDao extends BaseMapper<SongsVo> {
}
