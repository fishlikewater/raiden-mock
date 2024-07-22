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
package io.github.fishlikewater.raiden.mock.web.controller;

import io.github.fishlikewater.raiden.core.model.Result;
import io.github.fishlikewater.raiden.mock.service.MockService;
import io.github.fishlikewater.raiden.mock.service.cache.MockInterfaceCache;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code MockController}
 * mock请求
 *
 * @author zhangxiang
 * @version 1.0.0
 * @since 2024/07/15
 */
@RestController
public class MockController {

    private final MockService mockService;

    private final MockInterfaceCache mockInterface;

    public MockController(MockService mockService, MockInterfaceCache mockInterface) {
        this.mockService = mockService;
        this.mockInterface = mockInterface;
    }

    @RequestMapping("/**")
    public Object mockResult() throws Exception {
        return mockService.handleMock();
    }

    @RequestMapping("/reload")
    public Result<?> reload() throws Exception {
        mockInterface.load();
        return Result.of("200", "reload success");
    }
}
