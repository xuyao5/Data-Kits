package io.github.xuyao5.dal.generator.system;

import com.google.common.util.concurrent.RateLimiter;
import io.github.xuyao5.dal.generator.AbstractTest;
import io.github.xuyao5.dal.generator.configuration.GeneratorConfigBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MyCommonTest extends AbstractTest {

    @Autowired
    private GeneratorConfigBean generatorConfigBean;

    @Test
    void testApplicationProperties() {
        System.out.println("GeneratorDirRoot=" + generatorConfigBean.getGeneratorDirRoot());
    }

    @Test
    void RateLimiterTest() {
        RateLimiter rateLimiter = RateLimiter.create(2);

    }
}
