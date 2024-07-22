package io.github.fishlikewater.raiden.mock.service.generate;

import io.github.fishlikewater.raiden.core.RandomUtils;
import io.github.fishlikewater.raiden.mock.core.enums.DataTypeEnum;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

/**
 * {@code RandomGenerate}
 *
 * @author zhangxiang
 * @version 1.0.0
 * @since 2024/07/22
 */
@Service
public class RandomGenerate implements Generate {
    @Override
    public boolean support(DataTypeEnum type) {
        return type == DataTypeEnum.RANDOM;
    }

    @Override
    public Object generate(String expression) {
        String str = this.getExpression(expression);
        if (StringUtils.isBlank(str)) {
            return RandomUtils.randomNumberAndAlphabet(6);
        }
        int len = Integer.parseInt(str);
        return RandomUtils.randomNumberAndAlphabet(len);
    }
}
