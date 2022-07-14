package io.github.xuyao5.datakitsserver.mybatis;

import io.github.xuyao5.datakitsserver.context.AbstractTest;
import io.github.xuyao5.datakitsserver.dao.primary.mapper.OmsOrder1Mapper;
import io.github.xuyao5.datakitsserver.dao.primary.model.OmsOrder1;
import io.github.xuyao5.datakitsserver.dao.secondary.mapper.OmsOrder2Mapper;
import io.github.xuyao5.datakitsserver.dao.secondary.model.OmsOrder2;
import io.github.xuyao5.dkl.eskits.context.AbstractSequenceReporting;
import io.github.xuyao5.dkl.eskits.context.DisruptorBoost;
import io.github.xuyao5.dkl.eskits.util.DateUtilsPlus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
public class MyBatisTest extends AbstractTest {

    @Autowired
    private OmsOrder1Mapper sourceMapper;

    @Autowired
    private OmsOrder2Mapper targetMapper;

    @Test
    void mergeTest() {
        Date toDate = DateUtilsPlus.parse2Date("2022-12-31 23:59:59", DateUtilsPlus.STD_DATETIME_FORMAT);
        Date fromDate = DateUtilsPlus.parse2Date("2022-01-01 00:00:00", DateUtilsPlus.STD_DATETIME_FORMAT);
        DisruptorBoost.<OmsOrder1>context().create().processZeroArgEvent(OmsOrder1::new,
                //事件生产
                translator -> sourceMapper.streamQuery(fromDate, toDate, resultContext -> translator.translate((order, sequence) -> BeanUtils.copyProperties(resultContext.getResultObject(), order))),
                //错误处理
                (order, value) -> log.error("异常:{}|{}", value, order),
                //事件消费
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
    }

    @Test
    void singleInsertTest() {
        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
        Date toDate = DateUtilsPlus.parse2Date("2022-12-31 23:59:59", DateUtilsPlus.STD_DATETIME_FORMAT);
        Date fromDate = DateUtilsPlus.parse2Date("2022-01-01 00:00:00", DateUtilsPlus.STD_DATETIME_FORMAT);
        DisruptorBoost.<OmsOrder1>context().create().processZeroArgEvent(OmsOrder1::new,
                //事件生产
                translator -> sourceMapper.streamQuery(fromDate, toDate, resultContext -> translator.translate((order, sequence) -> BeanUtils.copyProperties(resultContext.getResultObject(), order))),
                //错误处理
                (order, value) -> log.error("异常:{}|{}", value, order),
                //事件消费
                (omsOrder1, sequence, endOfBatch) -> {
                    OmsOrder2 omsOrder2 = new OmsOrder2();
                    BeanUtils.copyProperties(omsOrder1, omsOrder2);
                    service.submit(() -> targetMapper.insertSelective(omsOrder2));
                });

        try {
            service.shutdown();
            boolean isClosed = service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            log.info("线程池关闭={}", isClosed);
        } catch (InterruptedException e) {
            service.shutdownNow();
            log.error("线程池关闭失败", e);
        }
    }
}