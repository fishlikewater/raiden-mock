package io.github.fishlikewater.raiden.mock.service.generate;

import io.github.fishlikewater.raiden.core.RandomUtils;
import io.github.fishlikewater.raiden.generate.GenerateUtils;
import io.github.fishlikewater.raiden.mock.core.enums.DataTypeEnum;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

/**
 * {@code AgeGenerate}
 *
 * @author zhangxiang
 * @version 1.0.0
 * @since 2024/07/22
 */
@Service
public class AgeGenerate implements Generate {

    @Override
    public boolean support(DataTypeEnum type) {
        return type == DataTypeEnum.AGE;
    }

    @Override
    public Object generate(String expression) {
        String str = this.getExpression(expression);
        if (StringUtils.isBlank(str)) {
            return GenerateUtils.AGE.generate();
        }
        String[] split = str.split(",");
        if (split.length == 2) {
            int from = Integer.parseInt(split[0].trim());
            int to = Integer.parseInt(split[1].trim());
            return RandomUtils.randomInt(from, to);
        }
        return GenerateUtils.AGE.generate();
    }
}
