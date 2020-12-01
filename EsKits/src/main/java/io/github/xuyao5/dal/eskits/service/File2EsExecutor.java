package io.github.xuyao5.dal.eskits.service;

import com.lmax.disruptor.dsl.Disruptor;
import io.github.xuyao5.dal.common.disruptor.DisruptorBolts;
import io.github.xuyao5.dal.common.file.FileLineBolts;
import io.github.xuyao5.dal.common.standard.StandardFileLine;
import io.github.xuyao5.dal.common.util.MyFileUtils;
import io.github.xuyao5.dal.eskits.configuration.xml.File2EsCollectorXml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.nio.charset.Charset;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 10/10/20 15:41
 * @apiNote File2EsExecutor
 * @implNote File2Es执行入口
 */
@Component("file2EsExecutor")
public final class File2EsExecutor {

    @Autowired
    private File2EsCollectorXml file2EsCollectorXml;

    public void execute(@NotNull String fileId) {
        file2EsCollectorXml.getFiles().seek(fileId).ifPresent(file2EsCollectorXmlFile -> {
            Disruptor<StandardFileLine> disruptor = DisruptorBolts.builder()
                    .build()
                    .startStandardFileLineDisruptor();
            //TODO:需要增加文件正则搜索
            FileLineBolts.builder()
                    .file(MyFileUtils.getFile(file2EsCollectorXmlFile.getPath()))
                    .charset(Charset.forName(file2EsCollectorXmlFile.getEncoding()))
                    .build()
                    .publishRecord(disruptor.getRingBuffer());
        });
    }
}
