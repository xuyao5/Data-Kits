package io.github.xuyao5.datakitsserver.job;

import io.github.xuyao5.datakitsserver.context.AbstractTest;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

/**
 * @author Thomas.XU(xuyao)
 * @version 18/07/22 22:44
 */
final class Db2DbJobTest extends AbstractTest {

    @Resource(name = "db2DbDemoJob")
    private Db2DbDemoJob db2DbDemoJob;

    @Test
    void test() {
        db2DbDemoJob.run();
    }
}
