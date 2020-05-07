package io.github.xuyao5.dal.snowflake.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 7/05/20 21:02
 * @apiNote SnowflakeKitsConfiguration
 * @implNote SnowflakeKitsConfiguration
 */
@Configuration
public class SnowflakeKitsConfiguration {

    @Bean
    public SnowflakeKitsConfigBean elasticsearchKitsConfigBean() {
        return SnowflakeKitsConfigBean.of();
    }
}
