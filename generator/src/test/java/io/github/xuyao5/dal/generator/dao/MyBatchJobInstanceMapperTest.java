package io.github.xuyao5.dal.generator.dao;

import io.github.xuyao5.dal.generator.AbstractTest;
import io.github.xuyao5.dal.generator.repository.paces.ext.MyBatchJobInstanceMapper;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.springframework.beans.factory.annotation.Autowired;

public class MyBatchJobInstanceMapperTest extends AbstractTest {

    @Autowired
    private MyBatchJobInstanceMapper myBatchJobInstanceMapper;

    void testSum() {
        final double sum = myBatchJobInstanceMapper.sum(SelectDSLCompleter.allRows());
        final double avg = myBatchJobInstanceMapper.avg(SelectDSLCompleter.allRows());
        final double max = myBatchJobInstanceMapper.max(SelectDSLCompleter.allRows());
        final double min = myBatchJobInstanceMapper.min(SelectDSLCompleter.allRows());
    }
}
