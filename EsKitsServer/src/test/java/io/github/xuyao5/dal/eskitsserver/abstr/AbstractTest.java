package io.github.xuyao5.dal.eskitsserver.abstr;

import io.github.xuyao5.dal.eskits.configuration.File2EsPropertyBean;
import io.github.xuyao5.dal.eskits.configuration.xml.File2EsCollectorXml;
import io.github.xuyao5.dal.eskitsserver.ElasticsearchKitsApplication;
import io.github.xuyao5.dal.eskitsserver.script.EsScriptSupporter;
import io.github.xuyao5.dal.eskitsserver.search.EsSearchSupporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = {ElasticsearchKitsApplication.class})
public abstract class AbstractTest {

    @Resource(name = "esSearchSupporter")
    protected EsSearchSupporter searchSupporter;

    @Resource(name = "esScriptSupporter")
    protected EsScriptSupporter scriptSupporter;

    @Autowired
    protected File2EsPropertyBean file2EsPropertyBean;

    @Autowired
    protected File2EsCollectorXml file2EsCollectorXml;
}
