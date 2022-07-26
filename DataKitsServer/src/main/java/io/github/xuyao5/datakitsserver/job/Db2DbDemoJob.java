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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
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
        Date toDate = DateUtilsPlus.parse2Date("2022-12-31 23:59:59", DateUtilsPlus.STD_DATETIME_FORMAT);
        Date fromDate = DateUtilsPlus.parse2Date("2022-01-01 00:00:00", DateUtilsPlus.STD_DATETIME_FORMAT);
        Date[][] dateSharding = DateUtilsPlus.dateSharding(fromDate, toDate, Runtime.getRuntime().availableProcessors());

        int threads = Runtime.getRuntime().availableProcessors() * 2;
        int batch = 100;
        ExecutorService executor = Executors.newWorkStealingPool(threads);
        for (Date[] dates : dateSharding) {
            executor.execute(() -> {
                int count = new Db2DbService<OmsOrder1>().execute(Db2DbConfig.of(), OmsOrder1::new,
                        //生产
                        handler -> sourceMapper.streamQuery(dates[0], dates[1], handler),
                        //消费
                        new AbstractSequenceReporting<OmsOrder1>(batch) {
                            @Override
                            protected void processEvent(List<OmsOrder1> list) {
                                int count = targetMapper.mergeSelective(list.stream().map(omsOrder1 -> {
                                    OmsOrder2 omsOrder2 = new OmsOrder2();
                                    BeanUtils.copyProperties(omsOrder1, omsOrder2);
                                    return omsOrder2;
                                }).collect(Collectors.toList()));
                                if (list.size() != count) {
                                    log.warn("有多条记录DUPLICATE，获取：{}，处理：{}，差额：{}", list.size(), count, count - list.size());
                                }
                            }
                        });
                log.info("获取记录数:{}", count);
            });
        }

        try {
            executor.shutdown();
            boolean termination = executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
            if (!termination) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}