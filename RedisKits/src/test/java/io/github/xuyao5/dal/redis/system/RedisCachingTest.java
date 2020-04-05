package io.github.xuyao5.dal.redis.system;

import io.github.xuyao5.dal.redis.AbstractTest;
import io.github.xuyao5.dal.redis.annotation.EnableRedisCaching;
import org.junit.jupiter.api.Test;

@EnableRedisCaching
class RedisCachingTest extends AbstractTest {

    @Test
    void testRedisCaching() {
        System.out.println("Hello World!");
    }
}
