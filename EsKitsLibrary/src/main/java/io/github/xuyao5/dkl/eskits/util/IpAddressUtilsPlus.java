package io.github.xuyao5.dkl.eskits.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.net.InetAddress;
import java.util.stream.IntStream;

/**
 * @author Thomas.XU(xuyao)
 * @version 11/03/21 11:47
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IpAddressUtilsPlus {

    @SneakyThrows
    public static int getIpAddressSum() {
        byte[] address = InetAddress.getLocalHost().getAddress();
        return IntStream.range(0, address.length).map(i -> address[i] & 0xFF).sum();
    }

    @SneakyThrows
    public static int getIpAddressHash() {
        byte[] address = InetAddress.getLocalHost().getAddress();
        return IntStream.range(0, address.length).map(i -> address[i] & 0xFF).hashCode();
    }
}