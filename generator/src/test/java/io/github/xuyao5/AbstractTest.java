package io.github.xuyao5;

import io.github.xuyao5.dal.generator.GeneratorApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GeneratorApplication.class})
public abstract class AbstractTest {

}
