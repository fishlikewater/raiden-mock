package io.github.fishlikewater.raiden.mock.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.fishlikewater.raiden.config.ConfigParser;
import io.github.fishlikewater.raiden.mock.service.cache.MockInterfaceCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@code AutoConfig}
 *
 * @author zhangxiang
 * @version 1.0.0
 * @since 2024/07/16
 */
@Configuration
public class AutoConfig {

    @Bean
    public ConfigParser configParser() {
        return new ConfigParser();
    }

    @Bean
    public MockInterfaceCache mockInterfaceCache(ObjectMapper objectMapper, ConfigParser configParser) {
        return new MockInterfaceCache(objectMapper, configParser);
    }
}
