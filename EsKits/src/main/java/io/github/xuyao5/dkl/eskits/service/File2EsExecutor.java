package io.github.xuyao5.dkl.eskits.service;

import io.github.xuyao5.dkl.eskits.configuration.xml.File2EsTasks;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import javax.xml.bind.JAXB;

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
        File2EsTasks file2EsTasks = JAXB.unmarshal(getClass().getClassLoader().getResource("File2EsCollector.xml"), File2EsTasks.class);
//        file2EsTasks.getTask().seek(fileId).ifPresent(file2EsCollectorXmlFile -> {
//            Disruptor<StandardFileLine> standardFileLineDisruptor = new DisruptorBolts().startStandardFileLineDisruptor();
//            //TODO:需要增加文件正则搜索
//            FileLineBolts fileLineBolts = new FileLineBolts(MyFileUtils.getFile(file2EsCollectorXmlFile.getPath()), Charset.forName(file2EsCollectorXmlFile.getEncoding()));
//            fileLineBolts.publishRecord(standardFileLineDisruptor.getRingBuffer());
//        });
    }
}