package io.github.xuyao5.dkl.eskits.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;

import javax.validation.constraints.NotNull;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 1/06/21 21:00
 * @apiNote MyCompressUtils
 * @implNote MyCompressUtils
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MyCompressUtils {

    @SneakyThrows
    public static void createTarGz(@NotNull List<Path> fileList) {
        try (TarArchiveOutputStream outputStream = (TarArchiveOutputStream) new ArchiveStreamFactory().createArchiveOutputStream("", new CompressorStreamFactory().createCompressorOutputStream(CompressorStreamFactory.GZIP, Files.newOutputStream(Paths.get("my.tar.gz"))))) {
            fileList.forEach(path -> {

            });
            outputStream.closeArchiveEntry();
            outputStream.finish();
        }
    }
}