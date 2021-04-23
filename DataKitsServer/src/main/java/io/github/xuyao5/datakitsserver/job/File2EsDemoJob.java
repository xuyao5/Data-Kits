package io.github.xuyao5.datakitsserver.job;

import io.github.xuyao5.datakitsserver.configuration.EsClientConfig;
import io.github.xuyao5.datakitsserver.vo.MyDocument;
import io.github.xuyao5.dkl.eskits.service.File2EsExecutor;
import io.github.xuyao5.dkl.eskits.service.ModifyByScrollExecutor;
import io.github.xuyao5.dkl.eskits.service.config.File2EsConfig;
import io.github.xuyao5.dkl.eskits.service.config.ModifyByScrollConfig;
import io.github.xuyao5.dkl.eskits.support.boost.AliasesSupporter;
import io.github.xuyao5.dkl.eskits.support.boost.SettingsSupporter;
import io.github.xuyao5.dkl.eskits.support.general.IndexSupporter;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component("file2EsDemoJob")
public final class File2EsDemoJob implements Runnable {

    @Autowired
    protected RestHighLevelClient esClient;

    @Autowired
    protected EsClientConfig esClientConfig;

    @Override
    public void run() {
        final String OLD_INDEX = "file2es_disruptor_0";
        final String NEW_INDEX = "file2es_disruptor_1";
        //获取配置文件并执行
//        File2EsTasks file2EsTasks = JAXB.unmarshal(ResourceUtils.getFile(CLASSPATH_URL_PREFIX + FILE2ES_CONFIG_XML), File2EsTasks.class);
//        file2EsTasks.seek(taskId).ifPresent(File2EsExecutor.builder().build()::execute);
        //1.获取文件和索引名称
        File2EsConfig config = File2EsConfig.of(new File("/Users/xuyao/Downloads/DISRUPTOR_1000W_T_00.txt"), NEW_INDEX);

        //2.写入索引
        new File2EsExecutor(esClient, esClientConfig.getEsBulkThreads()).execute(config, MyDocument::of, myDocument -> myDocument);

        //3.设置别名
        AliasesSupporter aliasesSupporter = AliasesSupporter.getInstance();
        aliasesSupporter.addAsToIndex();

        //4.迁移老索引数据
        new ModifyByScrollExecutor(esClient, esClientConfig.getEsBulkThreads()).upsertByScroll(ModifyByScrollConfig.of(OLD_INDEX, NEW_INDEX), MyDocument::of, myDocument -> null);

        //5.关闭老索引
        IndexSupporter.getInstance().close(esClient, OLD_INDEX).isAcknowledged();

        //6.升副本
        SettingsSupporter.getInstance().updateNumberOfReplicas(esClient, NEW_INDEX, 1);
    }
}