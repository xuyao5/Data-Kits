package io.github.xuyao5.dkl.common.handler;

import com.lmax.disruptor.RingBuffer;
import io.github.xuyao5.dkl.common.context.StandardFileLineDisruptor;
import io.github.xuyao5.dkl.common.util.MyFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.LineIterator;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 11/10/20 08:54
 * @apiNote FileLineHandler
 * @implNote FileLineHandler
 */
@Slf4j
public final class FileLineHandler {

    private final File file;
    private final Charset charset;

    public FileLineHandler(@NotNull File file, @NotNull Charset charset) {
        this.file = file;
        this.charset = charset;
    }

    public FileLineHandler(@NotNull File file) {
        this(file, StandardCharsets.UTF_8);
    }

    public void publishRecord(@NotNull RingBuffer<StandardFileLineDisruptor.StandardFileLine> ringBuffer) {
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