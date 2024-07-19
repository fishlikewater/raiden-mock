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
