package io.github.xuyao5.dal.eskits.abstr;

import io.github.xuyao5.dal.eskits.File2EsApplication;
import io.github.xuyao5.dal.eskits.configuration.File2EsPropertyBean;
import io.github.xuyao5.dal.eskits.configuration.xml.File2EsCollectorXml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {File2EsApplication.class})
public abstract class AbstractTest {

    @Autowired
    protected File2EsPropertyBean file2EsPropertyBean;

    @Autowired
    protected File2EsCollectorXml file2EsCollectorXml;
}
