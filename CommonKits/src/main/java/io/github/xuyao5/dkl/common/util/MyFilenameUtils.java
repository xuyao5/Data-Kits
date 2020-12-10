package io.github.xuyao5.dkl.common.util;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

import javax.validation.constraints.NotNull;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 13/10/20 14:11
 * @apiNote MyFilenameUtils
 * @implNote MyFilenameUtils
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MyFilenameUtils extends FilenameUtils {

    public static String getConfirmFilename(@NotNull String file, @NotNull String fileSeparator, @NotNull String confirmFilePrefix, @NotNull String confirmFileSuffix) {
        Object[] baseNameArray = Splitter.on(fileSeparator).omitEmptyStrings().trimResults().splitToList(getBaseName(file)).toArray();
        if (baseNameArray.length > 1) {
            baseNameArray[0] = confirmFilePrefix;
            return Joiner.on(EXTENSION_SEPARATOR).skipNulls().join(Joiner.on(fileSeparator).skipNulls().join(baseNameArray), confirmFileSuffix);
        }
        return file;
    }
}