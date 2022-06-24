package io.github.xuyao5.datakitsserver.mybatis;

import io.github.xuyao5.datakitsserver.context.AbstractTest;
import io.github.xuyao5.datakitsserver.mybatis.handler.File2EsEventHandler;
import io.github.xuyao5.datakitsserver.mybatis.mapper.DemoMapper;
import io.github.xuyao5.dkl.eskits.context.DisruptorBoost;
import io.github.xuyao5.dkl.eskits.schema.standard.StandardFileLine;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Thomas.XU(xuyao)
 * @version 24/06/22 12:53
 */
@Slf4j
public class MyBatisTest extends AbstractTest {

    @Autowired
    private DemoMapper demoMapper;

    @Test
    void mergeTest() {
        DisruptorBoost.<StandardFileLine>context().create().processOneArgEvent(StandardFileLine::of, translator -> demoMapper.streamQueryDemo(resultContext -> {
            try {
                if (!resultContext.isStopped()) {
                    translator.translate((standardFileLine, sequence, count) -> BeanUtils.copyProperties(resultContext.getResultObject(), standardFileLine), resultContext.getResultCount());
                }
            } catch (Exception ex) {
                resultContext.isStopped();
                log.error("ERROR", ex);
            } finally {
                log.info("DONE");
            }
        }), (standardFileLine, value) -> {
        }, true, new File2EsEventHandler(20));
    }
}