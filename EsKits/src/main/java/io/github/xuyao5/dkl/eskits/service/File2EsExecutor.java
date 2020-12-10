package io.github.xuyao5.dkl.eskits.service;

import com.lmax.disruptor.dsl.Disruptor;
import io.github.xuyao5.dkl.common.disruptor.DisruptorBolts;
import io.github.xuyao5.dkl.common.file.FileLineBolts;
import io.github.xuyao5.dkl.common.standard.StandardFileLine;
import io.github.xuyao5.dkl.common.util.MyFileUtils;
import io.github.xuyao5.dkl.eskits.configuration.xml.File2EsCollectorXml;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import javax.xml.bind.JAXB;
import java.nio.charset.Charset;

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
    public void execute(@NotNull String fileId) {
        File2EsCollectorXml file2EsCollectorXml = JAXB.unmarshal(getClass().getClassLoader().getResource("File2EsCollector.xml"), File2EsCollectorXml.class);
        file2EsCollectorXml.getFiles().seek(fileId).ifPresent(file2EsCollectorXmlFile -> {
            Disruptor<StandardFileLine> standardFileLineDisruptor = new DisruptorBolts().startStandardFileLineDisruptor();
            //TODO:需要增加文件正则搜索
            FileLineBolts fileLineBolts = new FileLineBolts(MyFileUtils.getFile(file2EsCollectorXmlFile.getPath()), Charset.forName(file2EsCollectorXmlFile.getEncoding()));
            fileLineBolts.publishRecord(standardFileLineDisruptor.getRingBuffer());
        });
    }
}
