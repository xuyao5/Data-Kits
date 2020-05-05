package io.github.xuyao5.dal.elasticsearch;

import io.github.xuyao5.dal.elasticsearch.configuration.ElasticsearchKitsConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {ElasticsearchApplication.class})
public abstract class AbstractTest {

    @Autowired
    protected ElasticsearchKitsConfigBean configBean;
}
