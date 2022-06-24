package io.github.xuyao5.datakitsserver.mybatis.handler;

import io.github.xuyao5.datakitsserver.mybatis.mapper.DemoMapper;
import io.github.xuyao5.dkl.eskits.context.AbstractSequenceReporting;
import io.github.xuyao5.dkl.eskits.schema.standard.StandardFileLine;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * @author Thomas.XU(xuyao)
 * @version 19/06/22 18:30
 */
@Slf4j
public class File2EsEventHandler extends AbstractSequenceReporting<StandardFileLine> {

    @Autowired
    private DemoMapper demoMapper;

    public File2EsEventHandler(int limit) {
        super(limit);
    }

    @SneakyThrows
    @Override
    protected void processEvent(List<StandardFileLine> list) {
        demoMapper.merge(list);
        log.info("{}|{}", list.size(), Arrays.toString(list.toArray()));
    }
}