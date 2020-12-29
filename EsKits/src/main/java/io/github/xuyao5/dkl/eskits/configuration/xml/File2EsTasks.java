package io.github.xuyao5.dkl.eskits.configuration.xml;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 11/10/20 00:48
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
 */
@Data(staticConstructor = "of")
public final class File2EsTasks {

    private List<File2EsTask> task;

    public Optional<File2EsTask> seek(@NotNull String id) {
        return task.parallelStream()
                .filter(file2EsTask -> file2EsTask.getId().equalsIgnoreCase(id))
                .filter(file2EsTask -> file2EsTask.isEnable())
                .findAny();
    }

    @Data(staticConstructor = "of")
    static class File2EsTask {

        private String id;
        private String name;
        private boolean enable;
        private String filePath;
        private String fileEncoding;
        private String filePattern;
        private String fileNameSeparator;
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
}