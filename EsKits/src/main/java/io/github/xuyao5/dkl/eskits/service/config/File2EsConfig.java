package io.github.xuyao5.dkl.eskits.service.config;

import lombok.Data;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 25/02/21 16:36
 * @apiNote File2EsConfig
 * @implNote File2EsConfig
 */
@Data(staticConstructor = "of")
public final class File2EsConfig {

    private final File file;
    private final String index;

    private Charset charset = StandardCharsets.UTF_8;
    private char recordSeparator = 0x1E;
    private String fileComments = "#";

    private String[] sortField;
    private String[] sortOrder;
}