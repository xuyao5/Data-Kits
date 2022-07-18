package io.github.xuyao5.dkl.eskits.service.config;

import lombok.Data;

/**
 * @author Thomas.XU(xuyao)
 * @version 18/07/22 22:24
 */
@Data(staticConstructor = "of")
public final class Db2DbConfig {

    private int threshold = 4096;

    private int bufferSize = 8192;
}