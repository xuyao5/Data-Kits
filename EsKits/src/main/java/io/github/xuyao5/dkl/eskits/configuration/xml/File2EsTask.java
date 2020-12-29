package io.github.xuyao5.dkl.eskits.configuration.xml;

import lombok.Data;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 29/12/20 15:09
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
 */
@Data(staticConstructor = "of")
public final class File2EsTask {

    private String id;
    private String name;
    private boolean enable;
    private String filePath;
    private String fileEncoding;
    private String filenameRegex;
    private String filenameSeparator;
    private String fileRecordSeparator;
    private String fileConfirmPrefix;
    private String fileConfirmSuffix;
    private boolean fileMetadataLine;
    private boolean fileSummaryLine;
    private int filePkColumn;
    private int fileUkColumn;
    private int fileBatchSize;
    private String fileComments;
    private String esClientHosts;
    private String esUsername;
    private String esPassword;
}