package io.github.xuyao5.dal.generator.system;

import io.github.xuyao5.AbstractTest;
import io.github.xuyao5.dal.generator.configuration.GeneratorPropertyBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MyCommonTest extends AbstractTest {

    @Autowired
    private GeneratorPropertyBean generatorPropertyBean;

    @Test
    void testApplicationProperties() {
        System.out.println("GeneratorDirRoot=" + generatorPropertyBean.getGeneratorDirRoot());
    }
}
