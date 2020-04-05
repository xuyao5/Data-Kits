package io.github.xuyao5.dal.redis.configuration.selector;

import io.github.xuyao5.dal.redis.annotation.EnableCachingKit;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;

public class CachingKitConfigurationSelector extends AdviceModeImportSelector<EnableCachingKit> {

    @Override
    protected String[] selectImports(AdviceMode adviceMode) {
        System.out.println("这是CachingKitConfigurationSelector");
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
