package io.github.xuyao5.dkl.eskits.context;

import io.github.xuyao5.dkl.eskits.helper.SnowflakeHelper;
import io.github.xuyao5.dkl.eskits.util.IpAddressUtilsNZ;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 12/01/21 18:28
 * @apiNote AbstractSupporter
 * @implNote AbstractSupporter
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractExecutor {

    protected final RestHighLevelClient client;

    protected final SnowflakeHelper snowflake = new SnowflakeHelper(IpAddressUtilsNZ.getIpAddressSum() % 32, IpAddressUtilsNZ.getIpAddressSum() % 32);
}
