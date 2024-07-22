package io.github.fishlikewater.raiden.mock.service.generate;

import io.github.fishlikewater.raiden.mock.core.enums.DataTypeEnum;
import org.springframework.stereotype.Service;

/**
 * {@code TimestampCurrentGenerate}
 *
 * @author zhangxiang
 * @version 1.0.0
 * @since 2024/07/22
 */
@Service
public class TimestampCurrentGenerate implements Generate {

    @Override
    public boolean support(DataTypeEnum type) {
        return type == DataTypeEnum.TIMESTAMP_CURRENT;
    }

    @Override
    public Object generate(String expression) {
        return System.currentTimeMillis();
    }
}
