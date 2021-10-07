package io.github.xuyao5.dkl.eskits.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author Thomas.XU(xuyao)
 * @version 1/06/21 21:00
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CompressUtilsPlus {

    public static final String TAR_GZ_FILE_EXTENSION = ".tar.gz";
    public static final String BAK_FILE_EXTENSION = ".bak";

    @SneakyThrows
    public static boolean createTarGz(@NonNull File file, boolean deleteFile) {
        try (TarArchiveOutputStream outputStream = (TarArchiveOutputStream) new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.TAR, new CompressorStreamFactory().createCompressorOutputStream(CompressorStreamFactory.GZIP, new BufferedOutputStream(Files.newOutputStream(Paths.get(FilenameUtils.removeExtension(file.toString()) + TAR_GZ_FILE_EXTENSION), StandardOpenOption.CREATE))))) {
            outputStream.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
            outputStream.setBigNumberMode(TarArchiveOutputStream.BIGNUMBER_POSIX);

            if (file.isFile()) {
                outputStream.putArchiveEntry(outputStream.createArchiveEntry(file, file.getName() + BAK_FILE_EXTENSION));
                try (BufferedInputStream inputStream = new BufferedInputStream(Files.newInputStream(file.toPath()))) {
                    IOUtils.copy(inputStream, outputStream);
                }
            }
            outputStream.closeArchiveEntry();
            outputStream.finish();
        }
        if (deleteFile && file.isFile()) {
            boolean isDeleted = file.delete();
            if (!isDeleted) {
                file.deleteOnExit();
            }
            return isDeleted;
        }
        return false;
    }

    public static boolean createTarGz(@NonNull File file) {
        return createTarGz(file, false);
    }
}