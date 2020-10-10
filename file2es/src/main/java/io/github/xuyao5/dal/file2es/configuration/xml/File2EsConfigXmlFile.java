package io.github.xuyao5.dal.file2es.configuration.xml;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 11/10/20 00:48
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
 */
@Data(staticConstructor = "of")
public final class File2EsConfigXmlFile {

    private @NotNull boolean enable;
    private @NotNull String name;
    private @NotNull String pattern;
    private @NotNull String confirm;
    private @NotNull String path;
    private String encoding = StandardCharsets.UTF_8.name();
    private int pkColumn = 1;
    private int uniqueColumn = 1;
    private boolean metadataLine = true;
    private char recordSeparator = 0x1E;
    private int batchSize = 1000;
    private String comments;
    private String lineMapper;
}