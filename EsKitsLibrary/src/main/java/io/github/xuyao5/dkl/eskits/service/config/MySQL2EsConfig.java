package io.github.xuyao5.dkl.eskits.service.config;

import lombok.Data;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 25/02/21 16:36
 * @apiNote MySQL2EsConfig
 * @implNote MySQL2EsConfig
 */
@Data(staticConstructor = "of")
public final class MySQL2EsConfig {

    private final String schema;
    private final String username;
    private final String password;
    private final String table;

    private String hostname;
    private int port;
    private long timeout;
}