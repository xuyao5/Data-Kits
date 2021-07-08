package io.github.xuyao5.dkl.eskits.listener.config;

import lombok.Data;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 25/02/21 16:36
 * @apiNote MySQLBinlogConfig
 * @implNote MySQLBinlogConfig
 */
@Data(staticConstructor = "of")
public final class MySQLBinlogConfig {

    private String hostname;
    private int port;
    private long timeout;
    private String schema;
    private String username;
    private String password;
}