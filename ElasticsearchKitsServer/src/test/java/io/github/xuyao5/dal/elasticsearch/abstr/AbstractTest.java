package io.github.xuyao5.dal.elasticsearch.abstr;

import io.github.xuyao5.dal.elasticsearch.ElasticsearchKitsApplication;
import io.github.xuyao5.dal.elasticsearch.script.EsScriptSupporter;
import io.github.xuyao5.dal.elasticsearch.search.EsSearchSupporter;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = {ElasticsearchKitsApplication.class})
public abstract class AbstractTest {

    @Resource(name = "esSearchSupporter")
    protected EsSearchSupporter searchSupporter;

    @Resource(name = "esScriptSupporter")
    protected EsScriptSupporter scriptSupporter;
}
