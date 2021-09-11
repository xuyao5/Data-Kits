package io.github.xuyao5.dkl.eskits.schema.httpfs;

import lombok.Data;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 11/09/21 19:11
 * @apiNote FileChecksum
 * @implNote FileChecksum
 */
@Data(staticConstructor = "of")
public final class FileChecksum {

    private String algorithm;
    private String bytes;
    private int length;
}
