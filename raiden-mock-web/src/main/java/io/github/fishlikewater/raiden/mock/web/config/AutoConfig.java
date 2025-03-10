/*
 * Copyright (c) 2024 zhangxiang (fishlikewater@126.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
