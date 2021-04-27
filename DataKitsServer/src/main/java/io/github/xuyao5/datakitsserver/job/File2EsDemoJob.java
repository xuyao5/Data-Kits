package io.github.xuyao5.datakitsserver.job;

import io.github.xuyao5.datakitsserver.configuration.EsClientConfig;
import io.github.xuyao5.datakitsserver.vo.MyDocument;
import io.github.xuyao5.dkl.eskits.service.File2EsExecutor;
import io.github.xuyao5.dkl.eskits.service.config.File2EsConfig;
import io.github.xuyao5.dkl.eskits.support.boost.AliasesSupporter;
import io.github.xuyao5.dkl.eskits.support.boost.SettingsSupporter;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component("file2EsDemoJob")
public final class File2EsDemoJob implements Runnable {

    @Autowired
    protected RestHighLevelClient esClient;

    @Autowired
    protected EsClientConfig esClientConfig;

    @Override
    public void run() {
        final String ALIAS = "FILE2ES_DISRUPTOR";
        final String NEW_INDEX = "file2es_disruptor_100w_" + System.currentTimeMillis();
        //获取配置文件并执行
//        File2EsTasks file2EsTasks = JAXB.unmarshal(ResourceUtils.getFile(CLASSPATH_URL_PREFIX + FILE2ES_CONFIG_XML), File2EsTasks.class);
//        file2EsTasks.seek(taskId).ifPresent(File2EsExecutor.builder().build()::execute);
        //1.获取文件和索引名称
        File2EsConfig config = File2EsConfig.of(new File("/Users/xuyao/Downloads/DISRUPTOR_100W_T_00.txt"), NEW_INDEX);
        config.setSortField(new String[]{"dateTag"});
        config.setSortOrder(new String[]{"desc"});
        log.info("获取参数[{}]", config);

        //2.写入索引
        new File2EsExecutor(esClient, esClientConfig.getEsBulkThreads()).execute(config, MyDocument::of, myDocument -> {
            //自定义计算
            if (myDocument.getCashAmount() > 100) {
                myDocument.setDiscount(3);
            }
            return myDocument;
        });
        log.info("文件[{}]写入索引[{}]完毕", config.getFile(), NEW_INDEX);

        //3.新索引加入使用writeIndex(true)进行引流
        AliasesSupporter aliasesSupporter = AliasesSupporter.getInstance();
        boolean migrate1 = aliasesSupporter.addWriteIndexToAlias(esClient, ALIAS, NEW_INDEX);
        log.info("迁移别名[{}]到[{}]返回[{}]", ALIAS, NEW_INDEX, migrate1);

        //4.迁移老索引数据
//        new ModifyByScrollExecutor(esClient, esClientConfig.getEsBulkThreads()).upsertByScroll(ModifyByScrollConfig.of(OLD_INDEX, NEW_INDEX), MyDocument::of, myDocument -> null);

        //5.关闭老索引
//        IndexSupporter.getInstance().close(esClient, OLD_INDEX).isAcknowledged();

        //6.移除writeIndex(false)索引
        boolean migrate2 = aliasesSupporter.removeNonWriteIndexFromAlias(esClient, ALIAS);
        log.info("移除非写索引从[{}]", ALIAS);

        //6.升副本
        SettingsSupporter.getInstance().updateNumberOfReplicas(esClient, NEW_INDEX, 1);
    }
}