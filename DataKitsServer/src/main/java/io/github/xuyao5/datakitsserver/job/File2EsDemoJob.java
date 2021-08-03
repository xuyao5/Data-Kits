package io.github.xuyao5.datakitsserver.job;

import io.github.xuyao5.datakitsserver.configuration.EsKitsConfig;
import io.github.xuyao5.datakitsserver.vo.MyFileDocument;
import io.github.xuyao5.dkl.eskits.service.File2EsService;
import io.github.xuyao5.dkl.eskits.service.config.File2EsConfig;
import io.github.xuyao5.dkl.eskits.support.batch.ReindexSupporter;
import io.github.xuyao5.dkl.eskits.support.boost.AliasesSupporter;
import io.github.xuyao5.dkl.eskits.support.boost.SettingsSupporter;
import io.github.xuyao5.dkl.eskits.support.general.IndexSupporter;
import io.github.xuyao5.dkl.eskits.util.FileUtilsPlus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.function.UnaryOperator;

@Slf4j
@Component("file2EsDemoJob")
public final class File2EsDemoJob implements Runnable {

    @Autowired
    protected RestHighLevelClient esClient;

    @Autowired
    protected EsKitsConfig esKitsConfig;

    @Override
    public void run() {
        final String ALIAS = "FILE2ES_DISRUPTOR";
        String basePath = "/Users/xuyao/Downloads";
        String filenameRegex = "^INT_DISRUPTOR_1W_T_\\d{8}_\\d{2}.txt$";
        FileUtilsPlus.getDecisionFiles(basePath, filenameRegex, path -> FileUtils.getFile(path.toString().replaceFirst("INT_", "P_").replaceFirst(".txt", ".log")).exists())
                .forEach(file -> {
                    //1.索引名称
                    char splitChar = '_';
                    String[] filenames = StringUtils.split(FilenameUtils.getBaseName(file.getName()), splitChar);
                    String index = StringUtils.join(ALIAS.toLowerCase(Locale.ROOT), splitChar, filenames[filenames.length - 2]);

                    //2.写入索引
                    long count = new File2EsService(esClient, esKitsConfig.getEsBulkThreads()).execute(File2EsConfig.of(file, index), MyFileDocument::of, UnaryOperator.identity());
                    log.info("文件[{}]写入索引[{}]完毕,共处理{}条数据", file, index, count);

                    //3.别名重定向
                    String[] indexArray = AliasesSupporter.getInstance().migrate(esClient, ALIAS, index);
                    log.info("迁移别名[{}]到[{}]返回[{}]", ALIAS, index, indexArray.length > 0 ? indexArray : "无别名迁移");

                    if (indexArray.length > 0) {
                        //4.迁移老索引数据
                        BulkByScrollResponse reindex = ReindexSupporter.getInstance().reindex(esClient, QueryBuilders.matchAllQuery(), index, esKitsConfig.getEsScrollSize(), indexArray);
                        log.info("迁移索引[{}]返回[{}]", indexArray, reindex);

                        //5.关闭老索引
                        boolean acknowledged = IndexSupporter.getInstance().close(esClient, indexArray).isAcknowledged();
                        log.info("关闭索引[{}]返回[{}]", indexArray, acknowledged);
                    }

                    //6.升副本
                    boolean isUpdateReplicasSuccess = SettingsSupporter.getInstance().updateNumberOfReplicas(esClient, index, esKitsConfig.getEsIndexReplicas());
                    log.info("升副本索引[{}]返回[{}]", index, isUpdateReplicasSuccess);

                    //7.压缩文件
//                    boolean isDelete = CompressUtilsPlus.createTarGz(file, false);
//                    log.info("压缩文件[{}]是否删除[{}]", file, isDelete);
                });
    }
}