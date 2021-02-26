package io.github.xuyao5.dkl.eskits.configuration;

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

    private int idColumn = 1;
    private int collapseColumn = 0;
    private Charset charset = StandardCharsets.UTF_8;
    private char recordSeparator = 0x1E;
    private boolean metadataLine = false;
    private int bulkSize = 15;
    private char fileComments = '#';

    private int ringBufferSize = 1 << 10;
}