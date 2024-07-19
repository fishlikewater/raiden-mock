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
