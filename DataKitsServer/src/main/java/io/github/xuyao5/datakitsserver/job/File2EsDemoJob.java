package io.github.xuyao5.datakitsserver.job;

import io.github.xuyao5.datakitsserver.configuration.elasticsearch.EsKitsConfig;
import io.github.xuyao5.datakitsserver.vo.MyFileDocument;
import io.github.xuyao5.dkl.eskits.service.File2EsService;
import io.github.xuyao5.dkl.eskits.service.config.File2EsConfig;
import io.github.xuyao5.dkl.eskits.support.auxiliary.AliasesSupporter;
import io.github.xuyao5.dkl.eskits.support.auxiliary.CleaningSupporter;
import io.github.xuyao5.dkl.eskits.support.batch.ReindexSupporter;
import io.github.xuyao5.dkl.eskits.support.general.IndexSupporter;
import io.github.xuyao5.dkl.eskits.util.DateUtilsPlus;
import io.github.xuyao5.dkl.eskits.util.FileUtilsPlus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component("file2EsDemoJob")
public final class File2EsDemoJob implements Runnable {

    @Autowired
    private RestHighLevelClient esClient;

    @Autowired
    private EsKitsConfig esKitsConfig;

    @Override
    public void run() {
        String basePath = "/Users/xuyao/Downloads";

        Map<String, String> fileMap = new ConcurrentHashMap<>();
        fileMap.put("FILE2ES_DISRUPTOR", "^INT_DISRUPTOR_1w_T_\\d{8}_\\d{2}.txt$");//可以从配置中读取

        fileMap.forEach((alias, filenameRegex) -> FileUtilsPlus.getDecisionFiles(basePath, filenameRegex, path -> FileUtils.getFile(path.toString().replaceFirst("INT_", "P_").replaceFirst(".txt", ".log")).exists()).forEach(file -> {
            //1.索引名称
            String splitChar = "_";
            String[] filenames = StringUtils.split(FilenameUtils.getBaseName(file.getName()), splitChar);
            String index = StringUtils.joinWith(splitChar, alias.toLowerCase(Locale.ROOT), filenames[filenames.length - 2]);
            log.info("根据文件名日期计算得到写入索引名:[{}]", index);

            //2.写入索引
            File2EsConfig file2EsConfig = File2EsConfig.of(file, index);
            file2EsConfig.setPriShards(1);
            file2EsConfig.setIdColumn(-1);
            long count = new File2EsService<MyFileDocument>(esClient, esKitsConfig.getEsBulkThreads()).execute(file2EsConfig, MyFileDocument::of, document -> {
                document.setLocation(new GeoPoint(-41.288837561602826, 174.77854717629864));
                return document;
            });
            if (count < 1) {
                log.warn("文件[{}]无数据写入索引[{}],请检查是否为空文件", file, index);
            } else {
                log.info("文件[{}]写入索引[{}]完毕,共处理{}条数据", file, index, count);

                //3.别名重定向
                String[] indexArray = AliasesSupporter.getInstance().migrate(esClient, alias, index);
                log.info("迁移别名[{}]到新索引[{}]，全索引{}", alias, index, indexArray.length > 0 ? indexArray : "无别名迁移");

                if (indexArray.length > 0) {
                    //4.迁移老索引数据，如果有rejected execution of coordinating operation reindex，调整ScrollSize
                    BulkByScrollResponse reindex = ReindexSupporter.getInstance().reindex(esClient, QueryBuilders.matchAllQuery(), index, esKitsConfig.getEsScrollSize(), indexArray);
                    log.info("迁移索引[{}]返回[{}]", indexArray, reindex);

                    //5.关闭老索引
                    boolean acknowledged = IndexSupporter.getInstance().close(esClient, indexArray).isAcknowledged();
                    log.info("关闭索引[{}]返回[{}]", indexArray, acknowledged);
                }

                //6.升副本
//                boolean isUpdateReplicasSuccess = SettingsSupporter.getInstance().updateNumberOfReplicas(esClient, index, esKitsConfig.getEsIndexReplicas());
//                log.info("索引[{}]升副本返回[{}]", index, isUpdateReplicasSuccess);
            }

            //7.压缩文件
/*            boolean isDelete = CompressUtilsPlus.createTarGz(file, false);
            log.info("压缩文件[{}]是否删除[{}]", file, isDelete);*/

            //8.清理历史>7天
            String deleteIndex = StringUtils.joinWith(splitChar, alias.toLowerCase(Locale.ROOT), "*");
            CleaningSupporter.getInstance().clearClosedIndex(esClient, deleteIndex, indices4Cat -> {
                String[] indexNameArray = StringUtils.split(indices4Cat.getIndex(), splitChar);
                return DateUtilsPlus.daysBetween(indexNameArray[indexNameArray.length - 1]) > 1;
            }).forEach(response -> log.info("执行删除索引返回[{}]", response.isAcknowledged()));
        }));
    }
}