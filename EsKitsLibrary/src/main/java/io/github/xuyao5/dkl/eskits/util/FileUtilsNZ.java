package io.github.xuyao5.dkl.eskits.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;

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
 * @implSpec 31/05/21 22:38
 * @apiNote MyFileUtils
 * @implNote MyFileUtils
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FileUtilsNZ {

    @SneakyThrows
    public static List<File> getDecisionFiles(@NonNull String basePath, @NonNull String filenameRegex) {
        try (DirectoryStream<Path> dataFileStream = Files.newDirectoryStream(Paths.get(basePath), path -> Pattern.matches(filenameRegex, path.getFileName().toString()))) {
            return StreamSupport.stream(dataFileStream.spliterator(), false).map(Path::toFile).collect(Collectors.toList());
        }
    }
}