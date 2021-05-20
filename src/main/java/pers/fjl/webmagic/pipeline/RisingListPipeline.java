package pers.fjl.webmagic.pipeline;

import org.springframework.stereotype.Component;
import pers.fjl.webmagic.po.RisingListPo;
import pers.fjl.webmagic.dao.RisingListDao;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;
import java.util.List;

/**
 * 将爬取到的数据文件存入数据库
 */
@Component
public class RisingListPipeline implements Pipeline {

    @Resource
    private RisingListDao risingListDao;

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<RisingListPo> risingListPos = resultItems.get("risingLists");
        for (RisingListPo risingListPo : risingListPos) {
            risingListDao.insert(risingListPo);
        }
    }

}
