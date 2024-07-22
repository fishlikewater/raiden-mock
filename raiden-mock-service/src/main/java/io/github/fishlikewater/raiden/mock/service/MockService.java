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
package io.github.fishlikewater.raiden.mock.service;

/**
 * {@code MockService}
 *
 * @author zhangxiang
 * @version 1.0.0
 * @since 2024/07/16
 */
public interface MockService {

    /**
     * 处理mock
     *
     * @param body 请求参数
     * @return {@code Object}
     * @throws Exception 异常
     */
    Object handleMock() throws Exception;
}
