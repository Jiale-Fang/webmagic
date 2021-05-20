package pers.fjl.webmagic.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import pers.fjl.webmagic.po.Artist;

@Repository
public interface ArtistDao extends BaseMapper<Artist> {
}
