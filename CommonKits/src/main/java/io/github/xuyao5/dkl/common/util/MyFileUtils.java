package io.github.xuyao5.dkl.common.util;

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
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 13/10/20 14:11
 * @apiNote MyFileUtils
 * @implNote MyFileUtils
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MyFileUtils extends FileUtils {

    @SneakyThrows
    public static List<File> getDecisionFiles(@NotNull String basePath, @NotNull String filenameRegex, @NotNull String fileConfirmRegex) {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(basePath), path -> {
            if (Pattern.matches(filenameRegex, path.getFileName().toString())) {

            }
            return false;
        })) {
            List<File> fileList = new CopyOnWriteArrayList<>();
            directoryStream.forEach(path -> fileList.add(path.toFile()));
            return fileList;
        }
    }
}