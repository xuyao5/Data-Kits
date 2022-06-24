package io.github.xuyao5.datakitsserver.mybatis;

import io.github.xuyao5.datakitsserver.context.AbstractTest;
import io.github.xuyao5.datakitsserver.dao.primary.mapper.OmsOrder1Mapper;
import io.github.xuyao5.datakitsserver.dao.primary.mapper.OmsOrder2Mapper;
import io.github.xuyao5.datakitsserver.dao.primary.model.OmsOrder1;
import io.github.xuyao5.dkl.eskits.context.AbstractSequenceReporting;
import io.github.xuyao5.dkl.eskits.context.DisruptorBoost;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class MyBatisTest extends AbstractTest {

    @Autowired
    private OmsOrder1Mapper order1Mapper;

    @Autowired
    private OmsOrder2Mapper order2Mapper;

    @Test
    void mergeTest() {
        DisruptorBoost.<OmsOrder1>context().create().processZeroArgEvent(OmsOrder1::new, translator -> {
            //生产
            order1Mapper.streamQuery(1, resultContext -> {
                translator.translate((order, sequence) -> BeanUtils.copyProperties(resultContext.getResultObject(), order));
            });
        }, (order, value) -> {
            //错误处理
            log.error("获取到{}|{}", value, order);
        }, true, new AbstractSequenceReporting<OmsOrder1>(2000) {
            @SneakyThrows
            @Override
            protected void processEvent(List<OmsOrder1> list) {
                Thread.sleep(100);
//                order2Mapper.mergeSelective(list);
                log.info("批处理：{}", list.size());
            }
        });
    }
}