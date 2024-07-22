package io.github.fishlikewater.raiden.mock.service.generate;

import io.github.fishlikewater.raiden.generate.GenerateUtils;
import io.github.fishlikewater.raiden.mock.core.enums.DataTypeEnum;
import org.springframework.stereotype.Service;

/**
 * {@code UserNameGenerate}
 *
 * @author zhangxiang
 * @version 1.0.0
 * @since 2024/07/22
 */
@Service
public class UserNameGenerate implements Generate {

    @Override
    public boolean support(DataTypeEnum type) {
        return type == DataTypeEnum.NAME;
    }

    @Override
    public Object generate(String expression) {
        return GenerateUtils.USER_NAME.generate();
    }
}
