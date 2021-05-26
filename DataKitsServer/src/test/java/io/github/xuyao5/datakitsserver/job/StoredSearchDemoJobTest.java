package io.github.xuyao5.datakitsserver.job;

import io.github.xuyao5.datakitsserver.context.AbstractTest;
import io.github.xuyao5.dkl.eskits.service.StoredSearchExecutor;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

final class StoredSearchDemoJobTest extends AbstractTest {

    @Resource(name = "storedSearchDemoJob")
    private StoredSearchDemoJob storedSearchDemoJob;

    @Test
    void test() {
        storedSearchDemoJob.run();
    }


    @Test
    void initial() {
        new StoredSearchExecutor(esClient).initial();
    }
}
