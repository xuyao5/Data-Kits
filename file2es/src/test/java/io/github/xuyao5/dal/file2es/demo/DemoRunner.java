package io.github.xuyao5.dal.file2es.demo;

import io.github.xuyao5.dal.file2es.abstr.AbstractTest;
import org.junit.jupiter.api.Test;

final class DemoRunner extends AbstractTest {

    @Test
    void test() {
        System.out.println("EskitsServerUrl属性=" + file2EsPropertyBean.getEskitsServerUrl());
        System.out.println("file2EsCollectorXml属性=" + file2EsCollectorXml.toString());
        file2EsCollectorXml.getFiles().seek("file1").ifPresent(file2EsCollectorXmlFile -> System.out.println("找到=" + file2EsCollectorXmlFile));
    }
}
