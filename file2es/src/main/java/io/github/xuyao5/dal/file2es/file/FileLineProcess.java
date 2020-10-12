package io.github.xuyao5.dal.file2es.file;

import com.lmax.disruptor.RingBuffer;
import io.github.xuyao5.dal.core.standard.StandardFileLine;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 11/10/20 08:54
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
 */
@Slf4j
@Component("fileLineProcess")
public final class FileLineProcess {

    public void recordPublish(@NotNull File file, @NotNull String charsetName, @NotNull RingBuffer<StandardFileLine> ringBuffer) {
        try (LineIterator lineIterator = FileUtils.lineIterator(file, charsetName)) {
            //最简化循环操作
            for (int i = 0; lineIterator.hasNext(); i++) {
                ringBuffer.publishEvent((standardFileLine, sequence, lineNo, lineRecord) -> {
                    standardFileLine.setLineNo(lineNo);
                    standardFileLine.setLineRecord(lineRecord);
                }, i, lineIterator.nextLine());
            }
        } catch (IOException ex) {
            log.error(ex.getLocalizedMessage(), ex);
        }
    }
}