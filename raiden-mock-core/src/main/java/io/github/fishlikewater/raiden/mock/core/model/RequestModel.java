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
package io.github.fishlikewater.raiden.mock.core.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * {@code RequestModel}
 *
 * @author zhangxiang
 * @version 1.0.0
 * @since 2024/07/15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestModel {

    private String fileName;

    @JsonAlias("request_url")
    private String url;

    @JsonAlias("request_method")
    private String method;

    @JsonAlias("response_code")
    private String responseCode;

    @JsonAlias("response_headers")
    private String[] responseHeaders;

    @JsonAlias("response_body")
    private Map<String, Object> responseBody;

    @JsonAlias("response_wrapper")
    private Map<String, Object> responseWrapper;
}
