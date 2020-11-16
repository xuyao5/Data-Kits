package io.github.xuyao5.dal.file2es.abstr;

import io.github.xuyao5.dal.file2es.File2EsApplication;
import io.github.xuyao5.dal.file2es.configuration.File2EsPropertyBean;
import io.github.xuyao5.dal.file2es.configuration.xml.File2EsCollectorXml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {File2EsApplication.class})
public abstract class AbstractTest {

    @Autowired
    protected File2EsPropertyBean file2EsPropertyBean;

    @Autowired
    protected File2EsCollectorXml file2EsCollectorXml;
}
