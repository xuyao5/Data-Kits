package io.github.xuyao5.datakitsserver.job;

import io.github.xuyao5.datakitsserver.dao.primary.mapper.OmsOrder1Mapper;
import io.github.xuyao5.datakitsserver.dao.primary.model.OmsOrder1;
import io.github.xuyao5.datakitsserver.vo.MyFileDocument;
import io.github.xuyao5.dkl.eskits.consts.DisruptorThresholdConst;
import io.github.xuyao5.dkl.eskits.context.boost.DuplicateBoost;
import io.github.xuyao5.dkl.eskits.support.auxiliary.AliasesSupporter;
import io.github.xuyao5.dkl.eskits.support.batch.BulkSupporter;
import io.github.xuyao5.dkl.eskits.support.general.IndexSupporter;
import io.github.xuyao5.dkl.eskits.support.mapping.AutoMappingSupporter;
import io.github.xuyao5.dkl.eskits.util.DateUtilsPlus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

import static io.github.xuyao5.dkl.eskits.util.DateUtilsPlus.STD_DATE_FORMAT;

/**
 * @author Thomas.XU(xuyao)
 * @version 18/07/22 23:33
 */
@Slf4j
@Component("db2EsDemoJob")
public class Db2EsDemoJob implements Runnable {

    @Autowired
    private OmsOrder1Mapper sourceMapper;

    @Autowired
    private RestHighLevelClient esClient;

    private volatile static boolean isDone = false;

    @Override
    public void run() {
        String ALIAS = "OMS_ORDER_LIST";
        String INDEX = StringUtils.join("order_list_", DateUtilsPlus.format2Date(DateUtils.addDays(DateUtilsPlus.now(), -1), STD_DATE_FORMAT));
        AutoMappingSupporter autoMappingSupporter = AutoMappingSupporter.getInstance();

        if (!IndexSupporter.getInstance().exists(esClient, INDEX)) {
            autoMappingSupporter.autoMappingField(esClient, INDEX, 1, 0, MyFileDocument.class);
        } else if (!autoMappingSupporter.isSameMapping(esClient, INDEX, MyFileDocument.class) && !isDone) {
            autoMappingSupporter.autoMappingField(esClient, INDEX, 1, 0, MyFileDocument.class);
            isDone = true;
        }

        DuplicateBoost.<OmsOrder1>context()
                //读取buffer
                .defaultBufferSize(DisruptorThresholdConst.BUFFER_SIZE.getThreshold() * 8)
                //写入threshold
                .defaultThreshold(DisruptorThresholdConst.BATCH_SIZE.getThreshold())
                //创建并执行
                .create().execute(OmsOrder1::new,
                        //生产
                        (handler) -> {
                            Date toDate = DateUtilsPlus.parse2Date("2022-12-31 23:59:59", DateUtilsPlus.STD_DATETIME_FORMAT);
                            Date fromDate = DateUtilsPlus.parse2Date("2022-01-01 00:00:00", DateUtilsPlus.STD_DATETIME_FORMAT);
                            sourceMapper.streamQuery(fromDate, toDate, handler);
                        },
                        //消费
                        omsOrder1List -> BulkSupporter.getInstance().bulk(esClient, omsOrder1List.stream().map(omsOrder1 -> {
                            MyFileDocument document = MyFileDocument.of();
                            document.setUuid(omsOrder1.getOrderId());
                            document.setDiscount1(1);
                            document.setDiscount2(2);
                            return BulkSupporter.buildIndexRequest(INDEX, String.valueOf(omsOrder1.getId()), document);
                        }).collect(Collectors.toList())));

        //3.别名重定向
        String[] indexArray = AliasesSupporter.getInstance().increase(esClient, ALIAS, INDEX);
        log.info("迁移别名[{}]到新索引[{}]，原索引{}", ALIAS, INDEX, indexArray.length > 0 ? indexArray : "无别名迁移");
    }
}