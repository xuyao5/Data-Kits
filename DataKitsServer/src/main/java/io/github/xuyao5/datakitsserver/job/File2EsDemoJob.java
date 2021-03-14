package io.github.xuyao5.datakitsserver.job;

import io.github.xuyao5.datakitsserver.configuration.EsClientConfig;
import io.github.xuyao5.datakitsserver.vo.MyDocument;
import io.github.xuyao5.dkl.eskits.client.EsClient;
import io.github.xuyao5.dkl.eskits.configuration.File2EsConfig;
import io.github.xuyao5.dkl.eskits.service.File2EsExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;

@Component("file2EsDemoJob")
public final class File2EsDemoJob implements Runnable {

    @Resource(name = "myEsClient")
    protected EsClient esClient;

    @Autowired
    protected EsClientConfig esClientConfig;

    @Override
    public void run() {
        //获取配置文件并执行
//        File2EsTasks file2EsTasks = JAXB.unmarshal(ResourceUtils.getFile(CLASSPATH_URL_PREFIX + FILE2ES_CONFIG_XML), File2EsTasks.class);
//        file2EsTasks.seek(taskId).ifPresent(File2EsExecutor.builder().build()::execute);
        File2EsConfig config = File2EsConfig.of(new File("/Users/xuyao/Downloads/DISRUPTOR_1000W_T_00.txt"), "file2es_disruptor_1", esClientConfig.getEsBulkThreads());
        new File2EsExecutor(esClient).execute(config, MyDocument::of, myDocument -> myDocument);
    }
}