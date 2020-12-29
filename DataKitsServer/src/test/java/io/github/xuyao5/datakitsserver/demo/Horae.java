package io.github.xuyao5.datakitsserver.demo;

import io.github.xuyao5.datakitsserver.abstr.AbstractTest;
import io.github.xuyao5.datakitsserver.job.File2EsJob;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

final class Horae extends AbstractTest {

    @Resource(name = "file2EsJob")
    private File2EsJob file2EsJob;

    @Test
    void test() {
        file2EsJob.doJob("file1");//taskId是Job的入参
    }
}