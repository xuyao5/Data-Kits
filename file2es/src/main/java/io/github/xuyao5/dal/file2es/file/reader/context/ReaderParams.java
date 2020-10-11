package io.github.xuyao5.dal.file2es.file.reader.context;

import lombok.Data;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.regex.Pattern;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 11/10/20 09:05
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
 */
@Data(staticConstructor = "of")
public final class ReaderParams {

    private Path path;
    private Charset charset;
    private Pattern pattern;
    private char filenameSeparator;
    private char recordSeparator;
    private String confirmPrefix;
    private String confirmSuffix;
    private boolean metadataLine;
    private boolean summaryLine;
    private int batchSize;
    private char comments;
}
