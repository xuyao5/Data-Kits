package io.github.xuyao5.dkl.eskits.abstr;

import io.github.xuyao5.dkl.eskits.client.EsClient;
import io.github.xuyao5.dkl.eskits.helper.SnowflakeHelper;
import io.github.xuyao5.dkl.eskits.util.MyIpAddressUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 12/01/21 18:28
 * @apiNote AbstractSupporter
 * @implNote AbstractSupporter
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractExecutor {

    protected final EsClient esClient;

    protected final SnowflakeHelper snowflake = new SnowflakeHelper(MyIpAddressUtils.getIpAddressSum() % 32, MyIpAddressUtils.getIpAddressInt() % 32);
}
