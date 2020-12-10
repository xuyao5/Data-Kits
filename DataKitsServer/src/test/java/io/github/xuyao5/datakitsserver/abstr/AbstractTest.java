package io.github.xuyao5.datakitsserver.abstr;

import io.github.xuyao5.datakitsserver.ElasticsearchKitsApplication;
import io.github.xuyao5.datakitsserver.script.EsScriptSupporter;
import io.github.xuyao5.datakitsserver.search.EsSearchSupporter;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = {ElasticsearchKitsApplication.class})
public abstract class AbstractTest {

    @Resource(name = "esSearchSupporter")
    protected EsSearchSupporter searchSupporter;

    @Resource(name = "esScriptSupporter")
    protected EsScriptSupporter scriptSupporter;
}
