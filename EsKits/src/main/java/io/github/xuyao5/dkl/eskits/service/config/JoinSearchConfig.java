package io.github.xuyao5.dkl.eskits.service.config;

import lombok.Data;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 10/06/21 21:24
 * @apiNote JoinSearchConfig
 * @implNote JoinSearchConfig
 */
@Data(staticConstructor = "of")
public final class JoinSearchConfig {

    private final String index1;
    private final String index2;
}