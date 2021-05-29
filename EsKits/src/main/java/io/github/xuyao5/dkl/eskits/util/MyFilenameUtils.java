package io.github.xuyao5.dkl.eskits.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 13/10/20 14:11
 * @apiNote MyFilenameUtils
 * @implNote MyFilenameUtils
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MyFilenameUtils extends FilenameUtils {

    @SneakyThrows
    public static List<File> getDecisionFiles(@NotNull String basePath, @NotNull String dataFileRegex, @NotNull String confirmFileRegex) {
        try (DirectoryStream<Path> dataFileStream = Files.newDirectoryStream(Paths.get(basePath), path -> Pattern.matches(dataFileRegex, path.getFileName().toString()));
             DirectoryStream<Path> confirmFileStream = Files.newDirectoryStream(Paths.get(basePath), path -> Pattern.matches(confirmFileRegex, path.getFileName().toString()))) {
            List<Path> confirmFilePathList = StreamSupport.stream(confirmFileStream.spliterator(), false).collect(Collectors.toList());
            return StreamSupport.stream(dataFileStream.spliterator(), true).flatMap(dataFilePath -> confirmFilePathList.stream().map(confirmFilePath -> {
                System.out.println(MyStringUtils.difference(dataFileRegex, confirmFileRegex));
                System.out.println(MyStringUtils.difference(confirmFileRegex, dataFileRegex));

                System.out.println(dataFilePath.getFileName().toString() + "|" + confirmFilePath.getFileName().toString());
                return new File("");
            })).collect(Collectors.toList());
        }
    }
}