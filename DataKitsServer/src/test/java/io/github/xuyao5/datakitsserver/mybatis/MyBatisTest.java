package io.github.xuyao5.datakitsserver.mybatis;

import io.github.xuyao5.datakitsserver.context.AbstractTest;
import io.github.xuyao5.datakitsserver.dao.primary.mapper.OmsOrder1Mapper;
import io.github.xuyao5.datakitsserver.dao.primary.mapper.OmsOrder2Mapper;
import io.github.xuyao5.datakitsserver.dao.primary.model.OmsOrder1;
import io.github.xuyao5.datakitsserver.dao.primary.model.OmsOrder2;
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
        DisruptorBoost.<OmsOrder1>context().create().processZeroArgEvent(OmsOrder1::new,
                //生产
                translator -> order1Mapper.streamQuery(1, resultContext -> translator.translate((order, sequence) -> BeanUtils.copyProperties(resultContext.getResultObject(), order))),
                //错误处理
                (order, value) -> log.error("获取到{}|{}", value, order),
                //消费完成立即关闭
                true,
                //消费
                new AbstractSequenceReporting<OmsOrder1>(Short.MAX_VALUE) {
                    @SneakyThrows
                    @Override
                    protected void processEvent(List<OmsOrder1> list) {
                        list.parallelStream().forEach(omsOrder1 -> {
                            OmsOrder2 omsOrder2 = new OmsOrder2();
                            BeanUtils.copyProperties(omsOrder1, omsOrder2);
                            order2Mapper.mergeSelective(omsOrder2);
                        });
                        log.info("批处理：{}", list.size());
                    }
                });
    }
}