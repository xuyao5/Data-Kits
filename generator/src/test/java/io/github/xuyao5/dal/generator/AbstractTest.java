package io.github.xuyao5.dal.generator;

import io.github.xuyao5.dal.generator.configuration.GeneratorPropertyBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {GeneratorApplication.class})
public abstract class AbstractTest {

    @Autowired
    protected GeneratorPropertyBean generatorPropertyBean;
}
