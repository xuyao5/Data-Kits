package io.github.xuyao5.dal.redis.configuration;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class RedisKitsConfiguration {

    @Bean
    public RedisKitsPropertyBean redisKitsPropertyBean() {
        return RedisKitsPropertyBean.of();
    }
}
