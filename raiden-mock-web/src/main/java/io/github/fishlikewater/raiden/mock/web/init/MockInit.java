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
package io.github.fishlikewater.raiden.mock.web.init;

import io.github.fishlikewater.raiden.mock.service.cache.MockInterfaceCache;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * {@code MockInit}
 * 初始化
 *
 * @author zhangxiang
 * @version 1.0.0
 * @since 2024/07/15
 */
@Component
@RequiredArgsConstructor
public class MockInit implements CommandLineRunner {

    private final MockInterfaceCache mockInterfaceCache;

    @Override
    public void run(String... args) throws Exception {
        mockInterfaceCache.load();
    }
}
