package io.github.fishlikewater.raiden.mock.service.generate;

import io.github.fishlikewater.raiden.generate.GenerateUtils;
import io.github.fishlikewater.raiden.mock.core.enums.DataTypeEnum;
import org.springframework.stereotype.Service;

/**
 * {@code AddressGenerate}
 *
 * @author zhangxiang
 * @version 1.0.0
 * @since 2024/07/22
 */
@Service
public class AddressGenerate implements Generate {

    @Override
    public boolean support(DataTypeEnum type) {
        return type == DataTypeEnum.ADDRESS;
    }

    @Override
    public Object generate(String expression) {
        return GenerateUtils.ADDRESS.generate().address();
    }
}
