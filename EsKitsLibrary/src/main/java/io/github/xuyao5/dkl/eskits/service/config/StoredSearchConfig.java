package io.github.xuyao5.dkl.eskits.service.config;

import lombok.Data;

/**
 * @author Thomas.XU(xuyao)
 * @version 28/03/21 21:44
 */
@Data(staticConstructor = "of")
public final class StoredSearchConfig {

    private final String searchName;
    private final String index;
}
