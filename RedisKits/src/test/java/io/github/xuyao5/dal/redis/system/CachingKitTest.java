package io.github.xuyao5.dal.redis.system;

import io.github.xuyao5.dal.redis.AbstractTest;
import io.github.xuyao5.dal.redis.annotation.EnableCachingKit;
import org.junit.jupiter.api.Test;

@EnableCachingKit
class CachingKitTest extends AbstractTest {

    @Test
    void testApplicationProperties() {
        System.out.println("Hello World!");
    }
}
