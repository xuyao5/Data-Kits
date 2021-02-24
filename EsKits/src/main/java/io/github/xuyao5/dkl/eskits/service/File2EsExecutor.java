package io.github.xuyao5.dkl.eskits.service;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import io.github.xuyao5.dkl.eskits.configuration.xml.File2EsTask;
import io.github.xuyao5.dkl.eskits.schema.StandardFileLine;
import io.github.xuyao5.dkl.eskits.util.MyFileUtils;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.LineIterator;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 10/10/20 15:41
 * @apiNote File2EsExecutor
 * @implNote File2Es执行入口
 */
@Slf4j
@Builder(toBuilder = true)
public final class File2EsExecutor {

    @SneakyThrows
    public void execute(@NotNull File2EsTask task, @NotNull File file, @NotNull Charset charset) {
        //1.获取待处理文件
        //2.读取
        //3.发送
        //4.ES
        int bufferSize = 1 << 10;
        Disruptor<StandardFileLine> disruptor = new Disruptor<>(StandardFileLine::new, bufferSize, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());
        disruptor.handleEventsWith((standardFileLine, sequence, endOfBatch) -> {
//            System.out.println(standardFileLine + "|" + sequence + "|" + endOfBatch);
        });
        RingBuffer<StandardFileLine> ringBuffer = disruptor.start();
        try (LineIterator lineIterator = MyFileUtils.lineIterator(file, charset.name())) {
            LongAdder longAdder = new LongAdder();
            while (lineIterator.hasNext()) {
                ringBuffer.publishEvent((standardFileLine, sequence, lineNo, lineRecord) -> {
                    standardFileLine.setLineNo(lineNo);
                    standardFileLine.setLineRecord(lineRecord);
                }, longAdder.intValue(), lineIterator.nextLine());
                longAdder.increment();
            }
        }
        System.out.println("完成");

//        List<File> decisionFiles = MyFileUtils.getDecisionFiles(task.getFilePath(), task.getFilenameRegex(), task.getFileConfirmRegex());

//        decisionFiles.stream()
//                .filter(file -> Files.exists(Paths.get(MyFilenameUtils.getConfirmFilename(file.getPath(), task.getFilenameSeparator(), task.getFileConfirmPrefix(), task.getFileConfirmSuffix()))))
//                .forEach(file -> {
//                    System.out.println(file);
//                });
    }
}