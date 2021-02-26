package io.github.xuyao5.datakitsserver.job;

import io.github.xuyao5.datakitsserver.vo.MyDocument;
import io.github.xuyao5.dkl.eskits.client.EsClient;
import io.github.xuyao5.dkl.eskits.configuration.File2EsConfig;
import io.github.xuyao5.dkl.eskits.service.File2EsExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;

@Component("file2EsDemoJob")
public final class File2EsDemoJob implements Runnable {

    @Resource(name = "myEsClient")
    protected EsClient esClient;

    @Override
    public void run() {
        //获取配置文件并执行
//        File2EsTasks file2EsTasks = JAXB.unmarshal(ResourceUtils.getFile(CLASSPATH_URL_PREFIX + FILE2ES_CONFIG_XML), File2EsTasks.class);
//        file2EsTasks.seek(taskId).ifPresent(File2EsExecutor.builder().build()::execute);
        File2EsConfig config = File2EsConfig.of(new File("/Users/xuyao/Downloads/DISRUPTOR_100W_T_00.txt"), "file2es_disruptor_1");
        new File2EsExecutor(esClient).execute(recordArray -> {
            MyDocument myDocument = MyDocument.of();
            myDocument.setUuid(recordArray[0]);
            myDocument.setAmount(Double.parseDouble(recordArray[1]));
            myDocument.setChinese(recordArray[2]);
            myDocument.setDate(new Date(Long.parseLong(recordArray[3])));
            return myDocument;
        }, config);

    }
}