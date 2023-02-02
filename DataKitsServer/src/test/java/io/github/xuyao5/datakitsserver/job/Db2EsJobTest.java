package io.github.xuyao5.datakitsserver.job;

import io.github.xuyao5.datakitsserver.context.AbstractTest;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

/**
 * @author Thomas.XU(xuyao)
 * @version 18/07/22 22:44
 */
final class Db2EsJobTest extends AbstractTest {

    @Resource(name = "db2EsDemoJob")
    private Db2EsDemoJob db2EsDemoJob;

    @Test
    void test() {
        db2EsDemoJob.run();
    }
}
