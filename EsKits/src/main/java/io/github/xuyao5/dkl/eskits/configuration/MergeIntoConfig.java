package io.github.xuyao5.dkl.eskits.configuration;

import lombok.Data;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 28/03/21 21:44
 * @apiNote ZeroDowntimeMigrationConfig
 * @implNote ZeroDowntimeMigrationConfig
 */
@Data(staticConstructor = "of")
public final class MergeIntoConfig {

    private final String sourceIndex;
    private final String targetIndex;
}
