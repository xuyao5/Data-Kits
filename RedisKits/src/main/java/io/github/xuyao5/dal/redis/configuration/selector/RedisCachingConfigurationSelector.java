package io.github.xuyao5.dal.redis.configuration.selector;

import io.github.xuyao5.dal.redis.annotation.EnableRedisCaching;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;

public class RedisCachingConfigurationSelector extends AdviceModeImportSelector<EnableRedisCaching> {

    @Override
    protected String[] selectImports(AdviceMode adviceMode) {
        System.out.println("这是RedisCachingConfigurationSelector，哈哈哈");
        switch (adviceMode) {
            case PROXY:
                return new String[0];
            case ASPECTJ:
                return new String[1];
            default:
                return null;
        }
    }
}
