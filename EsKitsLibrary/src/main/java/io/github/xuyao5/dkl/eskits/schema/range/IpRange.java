package io.github.xuyao5.dkl.eskits.schema.range;

import lombok.Data;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * @author Thomas.XU(xuyao)
 * @version 13/03/21 18:43
 */
@Data(staticConstructor = "of")
public final class IpRange implements Serializable {

    private final InetAddress min;
    private final InetAddress max;
}
