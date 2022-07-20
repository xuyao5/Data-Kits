package io.github.xuyao5.datakitsserver.job;

import io.github.xuyao5.datakitsserver.dao.primary.mapper.OmsOrder1Mapper;
import io.github.xuyao5.datakitsserver.dao.primary.model.OmsOrder1;
import io.github.xuyao5.datakitsserver.dao.secondary.mapper.OmsOrder2Mapper;
import io.github.xuyao5.datakitsserver.dao.secondary.model.OmsOrder2;
import io.github.xuyao5.dkl.eskits.context.AbstractSequenceReporting;
import io.github.xuyao5.dkl.eskits.service.Db2DbService;
import io.github.xuyao5.dkl.eskits.service.config.Db2DbConfig;
import io.github.xuyao5.dkl.eskits.util.DateUtilsPlus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Thomas.XU(xuyao)
 * @version 18/07/22 21:35
 */
@Slf4j
@Component("db2DbDemoJob")
public final class Db2DbDemoJob implements Runnable {

    @Autowired
    private OmsOrder1Mapper sourceMapper;

    @Autowired
    private OmsOrder2Mapper targetMapper;

    @Override
    public void run() {
        final int model = 1;
        Date toDate = DateUtilsPlus.parse2Date("2022-12-31 23:59:59", DateUtilsPlus.STD_DATETIME_FORMAT);
        Date fromDate = DateUtilsPlus.parse2Date("2022-01-01 00:00:00", DateUtilsPlus.STD_DATETIME_FORMAT);

        switch (model) {
            case 0:
                new Db2DbService<OmsOrder1>().execute(Db2DbConfig.of(), OmsOrder1::new,
                        //生产
                        handler -> sourceMapper.streamQuery(fromDate, toDate, 2000, handler),
                        //消费
                        new AbstractSequenceReporting<OmsOrder1>(5000) {
                            @Override
                            protected void processEvent(List<OmsOrder1> list) {
                                targetMapper.mergeSelective(list.stream().map(omsOrder1 -> {
                                    OmsOrder2 omsOrder2 = new OmsOrder2();
                                    BeanUtils.copyProperties(omsOrder1, omsOrder2);
                                    return omsOrder2;
                                }).collect(Collectors.toList()));
                                log.info("批处理：{}", list.size());
                            }
                        });
                break;
            case 1:
                new Db2DbService<OmsOrder1>().execute(Db2DbConfig.of(), OmsOrder1::new,
                        //生产
                        handler -> sourceMapper.streamQuery(fromDate, toDate, 888, handler),
                        //消费
                        omsOrder1 -> {
                            OmsOrder2 omsOrder2 = new OmsOrder2();
                            BeanUtils.copyProperties(omsOrder1, omsOrder2);
                            targetMapper.insertSelective(omsOrder2);
                        });
                break;
            default:
                break;
        }
    }
}