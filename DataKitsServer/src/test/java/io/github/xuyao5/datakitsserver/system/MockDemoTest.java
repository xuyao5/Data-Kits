package io.github.xuyao5.datakitsserver.system;

import io.github.xuyao5.datakitsserver.context.AbstractTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author Thomas.XU(xuyao)
 * @version 11/11/22 13:52
 */
@Slf4j
public class MockDemoTest extends AbstractTest {

    @Test
    void hello() {
        System.out.println("hello");
    }
}