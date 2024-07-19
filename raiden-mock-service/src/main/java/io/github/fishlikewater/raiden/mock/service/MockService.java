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
