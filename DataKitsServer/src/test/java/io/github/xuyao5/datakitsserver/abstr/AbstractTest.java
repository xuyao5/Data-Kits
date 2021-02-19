package io.github.xuyao5.datakitsserver.abstr;

import io.github.xuyao5.datakitsserver.DataKitsApplication;
import io.github.xuyao5.dkl.eskits.client.EsClient;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = {DataKitsApplication.class})
public abstract class AbstractTest {

    @Resource(name = "esClient1")
    protected EsClient esClient;
}
