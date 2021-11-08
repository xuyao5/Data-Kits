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
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Thomas.XU(xuyao)
 * @version 31/05/21 22:38
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FileUtilsPlus {

    @SneakyThrows
    public static List<File> getDecisionFiles(@NonNull String basePath, @NonNull String filenameRegex, Predicate<? super Path> filter) {
        try (DirectoryStream<Path> dataFileStream = Files.newDirectoryStream(Paths.get(basePath), path -> Pattern.matches(filenameRegex, path.getFileName().toString()))) {
            return StreamSupport.stream(dataFileStream.spliterator(), false).filter(filter).map(Path::toFile).collect(Collectors.toList());
        }
    }
}