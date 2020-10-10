package io.github.xuyao5.dal.file2es.demo;

import io.github.xuyao5.dal.file2es.abstr.AbstractTest;
import org.junit.jupiter.api.Test;

final class DemoRunner extends AbstractTest {

    @Test
    void test() {
        System.out.println("EskitsServerUrl属性=" + file2EsPropertyBean.getEskitsServerUrl());
    }
}
