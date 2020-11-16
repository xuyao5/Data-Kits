package io.github.xuyao5.dal.file2es.configuration.xml;

import lombok.Data;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 11/10/20 00:48
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
 */
@Data(staticConstructor = "of")
public final class File2EsCollectorXmlFile {

    private String id;
    private String name;
    private boolean enable;
    private String path;
    private String encoding;
    private String pattern;
    private String filenameSeparator;
    private String recordSeparator;
    private String confirmPrefix;
    private String confirmSuffix;
    private boolean metadataLine;
    private boolean summaryLine;
    private int pkColumn;
    private int ukColumn;
    private int batchSize;
    private String comments;
}