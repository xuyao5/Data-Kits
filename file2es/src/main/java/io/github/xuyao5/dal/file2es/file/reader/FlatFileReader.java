package io.github.xuyao5.dal.file2es.file.reader;

import com.google.common.base.Splitter;
import io.github.xuyao5.dal.file2es.file.reader.context.ReaderParams;
import io.github.xuyao5.dal.file2es.file.reader.context.ReaderResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 11/10/20 08:54
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
 */
@Slf4j
@Component("flatFileReader")
public final class FlatFileReader {

    public ReaderResult recordProcess(@NotNull ReaderParams params) {
        ReaderResult readerResult = ReaderResult.of();
        try (LineIterator lineIterator = FileUtils.lineIterator(params.getPath().toFile(), params.getCharset().name())) {
            while (lineIterator.hasNext()) {
                List<String> lineList = Splitter.on(params.getRecordSeparator())
                        .trimResults()
                        .omitEmptyStrings()
                        .splitToList(lineIterator.nextLine());
            }
        } catch (IOException ex) {
            log.error("", ex);
        } finally {
            log.info("");
        }
        return readerResult;
    }
}