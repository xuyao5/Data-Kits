package io.github.xuyao5.datakitsserver.service;

import io.github.xuyao5.datakitsserver.abstr.AbstractTest;
import io.github.xuyao5.dkl.eskits.configuration.xml.File2EsTask;
import io.github.xuyao5.dkl.eskits.service.File2EsExecutor;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

public class File2EsExecutorTest extends AbstractTest {

    @Test
    void test() {
        File2EsExecutor.builder().build().execute(File2EsTask.of(), null, StandardCharsets.UTF_8);
    }
}
