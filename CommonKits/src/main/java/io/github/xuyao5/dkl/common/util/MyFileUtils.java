package io.github.xuyao5.dkl.common.util;

import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 13/10/20 14:11
 * @apiNote MyFileUtils
 * @implNote MyFileUtils
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MyFileUtils extends FileUtils {

    @SneakyThrows
    public static List<File> getDecisionFiles(@NotNull String basePath, @NotNull String glob) {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(basePath), glob)) {
            List<File> fileList = Lists.newCopyOnWriteArrayList();
            directoryStream.forEach(path -> fileList.add(path.toFile()));
            return fileList;
        }
    }
}