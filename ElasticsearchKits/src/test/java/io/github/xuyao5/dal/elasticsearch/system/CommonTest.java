package io.github.xuyao5.dal.elasticsearch.system;

import io.github.xuyao5.dal.elasticsearch.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class CommonTest extends AbstractTest {

    @Test
    void testApplicationProperties() {
        Arrays.asList(configBean.getEsClientHosts()).forEach(str -> System.out.println(str));
        System.out.println(configBean.getEsClientUsername());
        System.out.println(configBean.getEsClientPassword());
    }
}
