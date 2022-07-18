package io.github.xuyao5.datakitsserver.job;

import io.github.xuyao5.datakitsserver.dao.primary.mapper.OmsOrder1Mapper;
import io.github.xuyao5.datakitsserver.dao.primary.model.OmsOrder1;
import io.github.xuyao5.datakitsserver.dao.secondary.mapper.OmsOrder2Mapper;
import io.github.xuyao5.datakitsserver.dao.secondary.model.OmsOrder2;
import io.github.xuyao5.dkl.eskits.service.Db2DbService;
import io.github.xuyao5.dkl.eskits.service.config.Db2DbConfig;
import io.github.xuyao5.dkl.eskits.util.DateUtilsPlus;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Thomas.XU(xuyao)
 * @version 18/07/22 21:35
 */
@Slf4j
@Component("db2DbDemoJob")
public final class Db2DbDemoJob implements Runnable {

    @Autowired
    private RestHighLevelClient esClient;

    @Autowired
    private OmsOrder1Mapper sourceMapper;

    @Autowired
    private OmsOrder2Mapper targetMapper;

    @Override
    public void run() {
        Db2DbConfig config = Db2DbConfig.of();
        config.setThreshold(10000);
        new Db2DbService<OmsOrder1, OmsOrder2>().execute(config, OmsOrder1::new, OmsOrder2::new,
                //生产
                (handler) -> {
                    Date toDate = DateUtilsPlus.parse2Date("2022-12-31 23:59:59", DateUtilsPlus.STD_DATETIME_FORMAT);
                    Date fromDate = DateUtilsPlus.parse2Date("2022-01-01 00:00:00", DateUtilsPlus.STD_DATETIME_FORMAT);
                    sourceMapper.streamQuery(fromDate, toDate, handler);
                },
                //消费
                targetMapper::mergeSelective);
    }
}
