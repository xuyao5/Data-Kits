package io.github.xuyao5.dal.flatfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {FlatFileApplication.class})
public abstract class AbstractTest {

    @Autowired
    protected FlatFileFactory flatFileFactory;
}
