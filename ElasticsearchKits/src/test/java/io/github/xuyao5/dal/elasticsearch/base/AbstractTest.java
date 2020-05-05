package io.github.xuyao5.dal.elasticsearch.base;

import io.github.xuyao5.dal.elasticsearch.ElasticsearchKitsFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = {ElasticsearchApplication.class})
public abstract class AbstractTest {

    @Resource(name = "elasticsearchKitsFactory")
    protected ElasticsearchKitsFactory factory;
}
