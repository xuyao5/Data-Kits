package io.github.xuyao5.datakitsserver.job;

import io.github.xuyao5.datakitsserver.context.AbstractTest;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

final class StoredSearchDemoJobTest extends AbstractTest {

    @Resource(name = "storedSearchDemoJob")
    private StoredSearchDemoJob storedSearchDemoJob;

    @Test
    void test() {
        storedSearchDemoJob.run();
    }
}
