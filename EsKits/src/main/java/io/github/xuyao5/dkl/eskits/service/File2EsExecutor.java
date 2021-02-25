package io.github.xuyao5.dkl.eskits.service;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import io.github.xuyao5.dkl.eskits.abstr.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.client.EsClient;
import io.github.xuyao5.dkl.eskits.schema.StandardFileLine;
import io.github.xuyao5.dkl.eskits.support.IndexSupporter;
import io.github.xuyao5.dkl.eskits.support.batch.BulkSupporter;
import io.github.xuyao5.dkl.eskits.util.MyFileUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.LineIterator;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 10/10/20 15:41
 * @apiNote File2EsExecutor
 * @implNote File2Es执行入口
 */
@Slf4j
public final class File2EsExecutor extends AbstractExecutor {

    public File2EsExecutor(EsClient esClient) {
        super(esClient);
    }

    @SneakyThrows
    public void execute(Function<StandardFileLine, ? extends Serializable> mapper, @NotNull File file, @NotNull Charset charset, @NotNull String index) {
        //检查文件和索引是否存在
        if (!file.exists() || !esClient.execute(restHighLevelClient -> new IndexSupporter(restHighLevelClient).exists(index))) {
            return;
        }

        Disruptor<StandardFileLine> disruptor = new Disruptor<>(StandardFileLine::new, 1 << 10, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());
        disruptor.handleEventsWith((standardFileLine, sequence, endOfBatch) -> {
            //事件处理
//            System.out.println(standardFileLine + "|" + sequence + "|" + endOfBatch);
            esClient.execute(restHighLevelClient -> new BulkSupporter(restHighLevelClient).bulk(function -> {
                function.apply(BulkSupporter.buildIndexRequest(index, "", mapper.apply(standardFileLine)));
                return 30;
            }));
        });
        RingBuffer<StandardFileLine> ringBuffer = disruptor.start();
        try (LineIterator lineIterator = MyFileUtils.lineIterator(file, charset.name())) {
            LongAdder longAdder = new LongAdder();
            while (lineIterator.hasNext()) {
                longAdder.increment();
                ringBuffer.publishEvent((standardFileLine, sequence, lineNo, lineRecord) -> {
                    standardFileLine.setLineNo(lineNo);
                    standardFileLine.setLineRecord(lineRecord);
                }, longAdder.intValue(), lineIterator.nextLine());
            }
        }

//        List<File> decisionFiles = MyFileUtils.getDecisionFiles(task.getFilePath(), task.getFilenameRegex(), task.getFileConfirmRegex());

//        decisionFiles.stream()
//                .filter(file -> Files.exists(Paths.get(MyFilenameUtils.getConfirmFilename(file.getPath(), task.getFilenameSeparator(), task.getFileConfirmPrefix(), task.getFileConfirmSuffix()))))
//                .forEach(file -> {
//                    System.out.println(file);
//                });
    }
}