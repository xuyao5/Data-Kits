package io.github.xuyao5.dkl.eskits.helper;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author Thomas.XU(xuyao)
 * @version 21/06/21 23:23
 */
public final class ResourceHelper {

    @SneakyThrows
    public String getResourceFile(@NonNull String name, Charset charset) {
        return FileUtils.readFileToString(Objects.requireNonNull(FileUtils.toFile(getClass().getResource(name))), charset);
    }

    public String getResourceFile(@NonNull String name) {
        return getResourceFile(name, StandardCharsets.UTF_8);
    }
}
