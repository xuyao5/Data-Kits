package io.github.xuyao5.dal.file2es.demo;

import io.github.xuyao5.dal.file2es.File2EsExecutor;
import io.github.xuyao5.dal.file2es.abstr.AbstractTest;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

final class DemoRunner extends AbstractTest {

    @Resource(name = "file2EsExecutor")
    private File2EsExecutor file2EsExecutor;

    @Test
    void test() {
        System.out.println("EskitsServerUrl属性=" + file2EsPropertyBean.getEskitsServerUrl());
        System.out.println("file2EsCollectorXml属性=" + file2EsCollectorXml.toString());
        file2EsExecutor.execute("file1");
    }
}
