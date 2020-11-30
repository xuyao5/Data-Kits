package io.github.xuyao5.dal.common.file;

import com.lmax.disruptor.RingBuffer;
import io.github.xuyao5.dal.common.standard.StandardFileLine;
import io.github.xuyao5.dal.common.util.MyFileUtils;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.LineIterator;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 11/10/20 08:54
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
 */
@Slf4j
@Builder(toBuilder = true)
public final class FileLineProcess {

    private final File file;
    private final Charset charset;

    public void recordPublish(@NotNull RingBuffer<StandardFileLine> ringBuffer) {
        try (LineIterator lineIterator = MyFileUtils.lineIterator(file, charset.name())) {
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