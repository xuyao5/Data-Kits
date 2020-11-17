package io.github.xuyao5.dal.eskits.service;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import io.github.xuyao5.dal.common.file.FileLineProcess;
import io.github.xuyao5.dal.common.standard.StandardFileLine;
import io.github.xuyao5.dal.common.util.MyFileUtils;
import io.github.xuyao5.dal.eskits.configuration.xml.File2EsCollectorXml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.nio.charset.Charset;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 10/10/20 15:41
 * @apiNote TODO 这里输入文件说明
 * @implNote File2Es执行入口
 */
@Component("file2EsExecutor")
public final class File2EsExecutor {

    @Autowired
    private File2EsCollectorXml file2EsCollectorXml;

    @Resource(name = "fileLineProcess")
    private FileLineProcess fileLineProcess;

    public void execute(@NotNull String fileId) {
        file2EsCollectorXml.getFiles().seek(fileId).ifPresent(file2EsCollectorXmlFile -> {
            int bufferSize = 1 << 10;
            Disruptor<StandardFileLine> disruptor = new Disruptor<>(() -> StandardFileLine.of(), bufferSize, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());
            disruptor.handleEventsWith((event, sequence, endOfBatch) -> System.out.println("Event: " + event));
            disruptor.start();
            //TODO:需要增加文件正则搜索
            fileLineProcess.recordPublish(MyFileUtils.getFile(file2EsCollectorXmlFile.getPath()), Charset.forName(file2EsCollectorXmlFile.getEncoding()).name(), disruptor.getRingBuffer());
        });
    }
}
