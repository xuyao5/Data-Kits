package io.github.xuyao5.dal.redis.configuration.selector;

import io.github.xuyao5.dal.redis.annotation.EnableRedisCaching;
import io.github.xuyao5.dal.redis.configuration.RedisKitsConfiguration;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;
import org.springframework.context.annotation.AutoProxyRegistrar;

public class RedisCachingConfigurationSelector extends AdviceModeImportSelector<EnableRedisCaching> {

    private static final String CACHE_ASPECT_CONFIGURATION_CLASS_NAME = "org.springframework.cache.aspectj.AspectJCachingConfiguration";

    @Override
    protected String[] selectImports(AdviceMode adviceMode) {
        System.out.println("这是RedisCachingConfigurationSelector，哈哈哈");
        switch (adviceMode) {
            case PROXY:
                return getProxyImports();
            case ASPECTJ:
                return getAspectJImports();
            default:
                return null;
        }
    }

    private String[] getProxyImports() {
        return new String[]{AutoProxyRegistrar.class.getName(), RedisKitsConfiguration.class.getName()};
    }

    private String[] getAspectJImports() {
        return new String[]{CACHE_ASPECT_CONFIGURATION_CLASS_NAME};
    }
}
