package io.github.xuyao5.datakitsserver.job;

import io.github.xuyao5.datakitsserver.context.AbstractTest;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

final class ModifyByScrollTest extends AbstractTest {

    @Resource(name = "modifyByScrollJob")
    private ModifyByScrollJob modifyByScrollJob;

    @Test
    void test() {
        modifyByScrollJob.run();
    }
}
