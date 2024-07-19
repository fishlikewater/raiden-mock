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
