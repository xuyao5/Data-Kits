package io.github.xuyao5.dkl.eskits.service.handler;

import io.github.xuyao5.dkl.eskits.context.SequenceReportingHandler;
import io.github.xuyao5.dkl.eskits.schema.standard.StandardFileLine;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author Thomas.XU(xuyao)
 * @version 19/06/22 18:30
 */
public class File2EsEventHandler extends SequenceReportingHandler<StandardFileLine> {

    public File2EsEventHandler(int limit) {
        super(limit);
    }

    @SneakyThrows
    @Override
    protected void processEvent(List<StandardFileLine> list) {
        Thread.sleep(100);
        System.out.println(StringUtils.joinWith("|", list.size(), Arrays.toString(list.toArray())));
    }
}