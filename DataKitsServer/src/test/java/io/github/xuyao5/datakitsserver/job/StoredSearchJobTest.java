package io.github.xuyao5.datakitsserver.job;

import io.github.xuyao5.datakitsserver.context.AbstractTest;
import io.github.xuyao5.dkl.eskits.service.StoredSearchService;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

final class StoredSearchJobTest extends AbstractTest {

    @Resource(name = "storedSearchJob")
    private StoredSearchDemoJob storedSearchDemoJob;

    @Test
    void test() {
        storedSearchDemoJob.run();
    }


    @Test
    void initial() {
        new StoredSearchService(esClient).initial();
    }
}
