package io.github.xuyao5.datakitsserver.job;

import io.github.xuyao5.datakitsserver.context.AbstractTest;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

final class JoinSearchDemoJobTest extends AbstractTest {

    @Resource(name = "joinSearchDemoJob")
    private JoinSearchDemoJob joinSearchDemoJob;

    @Test
    void test() {
        joinSearchDemoJob.run();
    }
}
