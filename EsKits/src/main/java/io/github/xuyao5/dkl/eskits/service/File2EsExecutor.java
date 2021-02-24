package io.github.xuyao5.dkl.eskits.service;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import io.github.xuyao5.dkl.common.schema.StandardFileLine;
import io.github.xuyao5.dkl.eskits.configuration.xml.File2EsTask;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;

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
    public void execute(@NotNull File2EsTask task) {
        //1.获取待处理文件
        //2.读取
        //3.发送
        //4.ES
        int bufferSize = 1 << 10;
        Disruptor<StandardFileLine> disruptor = new Disruptor<>(() -> StandardFileLine.of(), bufferSize, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());
        disruptor.handleEventsWith((standardFileLine, sequence, endOfBatch) -> {
            System.out.println(standardFileLine + "|" + sequence + "|" + endOfBatch);
        });
        final RingBuffer<StandardFileLine> ringBuffer = disruptor.start();
        for (int i = 0; i < 1000000; i++) {
            ringBuffer.publishEvent((standardFileLine, sequence, lineNo, lineRecord) -> {
                standardFileLine.setLineNo(lineNo);
                standardFileLine.setLineRecord(lineRecord);
            }, i, String.valueOf(Math.random()));
        }


        disruptor.shutdown();


//        List<File> decisionFiles = MyFileUtils.getDecisionFiles(task.getFilePath(), task.getFilenameRegex(), task.getFileConfirmRegex());

//        decisionFiles.stream()
//                .filter(file -> Files.exists(Paths.get(MyFilenameUtils.getConfirmFilename(file.getPath(), task.getFilenameSeparator(), task.getFileConfirmPrefix(), task.getFileConfirmSuffix()))))
//                .forEach(file -> {
//                    System.out.println(file);
//                });
    }
}