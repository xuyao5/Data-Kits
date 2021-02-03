package io.github.xuyao5.datakitsserver.abstr;

import io.github.xuyao5.datakitsserver.DataKitsApplication;
import io.github.xuyao5.dkl.eskits.client.EsClient;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {DataKitsApplication.class})
public abstract class AbstractTest {

    protected EsClient getEsClient() {
        return new EsClient(new String[]{"localhost:9200"}, "", "");
    }
}
