package io.github.xuyao5.datakitsserver.job;

import io.github.xuyao5.datakitsserver.context.AbstractTest;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

final class MySQL2EsJobTest extends AbstractTest {

    @Resource(name = "mySQL2EsDemoJob")
    private MySQL2EsDemoJob mySQL2EsDemoJob;

    @Test
    void test() {
        mySQL2EsDemoJob.run();
    }
}