package io.github.xuyao5.dkl.eskits.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.net.InetAddress;
import java.util.stream.IntStream;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 11/03/21 11:47
 * @apiNote MyIpAddressUtils
 * @implNote MyIpAddressUtils
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MyIpAddressUtils {

    @SneakyThrows
    public static int getIpAddressInt() {
        int result = 0;
        for (byte b : InetAddress.getLocalHost().getAddress()) {
            result = result << 8 | (b & 0xFF);
        }
        return result;
    }

    @SneakyThrows
    public static int getIpAddressSum() {
        byte[] address = InetAddress.getLocalHost().getAddress();
        return IntStream.range(0, address.length).map(i -> address[i] & 0xFF).sum();
    }
}
