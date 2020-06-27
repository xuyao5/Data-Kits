package io.github.xuyao5.dal.elasticsearch.abstr;

import io.github.xuyao5.dal.elasticsearch.ElasticsearchApplication;
import io.github.xuyao5.dal.elasticsearch.search.EsSearchSupporter;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = {ElasticsearchApplication.class})
public abstract class AbstractTest {

    @Resource(name = "esSearchSupporter")
    protected EsSearchSupporter searchSupporter;
}
