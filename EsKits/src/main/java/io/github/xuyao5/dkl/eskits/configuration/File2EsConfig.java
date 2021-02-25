package io.github.xuyao5.dkl.eskits.configuration;

import lombok.Data;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 25/02/21 16:36
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
 */
@Data(staticConstructor = "of")
public final class File2EsConfig {

    private final File file;
    private final String index;
    private final int pkColumn;

    private int ukColumn = 0;
    private Charset charset = StandardCharsets.UTF_8;
    private char recordSeparator = 0x1E;
    private boolean metadataLine = true;
    private char fileComments = '#';
}
