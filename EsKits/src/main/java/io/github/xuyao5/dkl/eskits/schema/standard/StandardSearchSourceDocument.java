package io.github.xuyao5.dkl.eskits.schema.standard;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 20/03/21 20:47
 * @apiNote StoredSearchDocument
 * @implNote StoredSearchDocument
 */
@Data(staticConstructor = "of")
public final class StandardSearchSourceDocument implements Serializable {

    private final String searchName;
    private final String query;
}
