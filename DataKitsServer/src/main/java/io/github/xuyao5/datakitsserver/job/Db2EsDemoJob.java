package io.github.xuyao5.datakitsserver.job;

import io.github.xuyao5.datakitsserver.dao.primary.mapper.OmsOrder1Mapper;
import io.github.xuyao5.datakitsserver.dao.primary.model.OmsOrder1;
import io.github.xuyao5.datakitsserver.vo.MyFileDocument;
import io.github.xuyao5.dkl.eskits.consts.DisruptorThresholdConst;
import io.github.xuyao5.dkl.eskits.context.boost.DuplicateBoost;
import io.github.xuyao5.dkl.eskits.support.batch.BulkSupporter;
import io.github.xuyao5.dkl.eskits.support.mapping.AutoMappingSupporter;
import io.github.xuyao5.dkl.eskits.util.DateUtilsPlus;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

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

    @Override
    public void run() {
        String INDEX = "db2es";
        AutoMappingSupporter.getInstance().mapping(esClient, INDEX, 1, MyFileDocument.class);
        DuplicateBoost.<OmsOrder1>context()
                //读取buffer
                .defaultBufferSize(DisruptorThresholdConst.BUFFER_SIZE.getThreshold() * 4)
                //写入threshold
                .defaultThreshold(DisruptorThresholdConst.BATCH_SIZE.getThreshold() * 2)
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
                            return BulkSupporter.buildIndexRequest(INDEX, document);
                        }).collect(Collectors.toList())));
    }
}