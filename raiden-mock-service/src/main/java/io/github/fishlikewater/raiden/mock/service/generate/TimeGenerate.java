package io.github.fishlikewater.raiden.mock.service.generate;

import io.github.fishlikewater.raiden.generate.GenerateUtils;
import io.github.fishlikewater.raiden.mock.core.enums.DataTypeEnum;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

/**
 * {@code TimeGenerate}
 *
 * @author zhangxiang
 * @version 1.0.0
 * @since 2024/07/22
 */
@Service
public class TimeGenerate implements Generate {

    @Override
    public boolean support(DataTypeEnum type) {
        return type == DataTypeEnum.TIME;
    }

    @Override
    public Object generate(String expression) {
        String format = this.getExpression(expression);
        if (StringUtils.isBlank(format)) {
            return GenerateUtils.TIME.generate();
        }
        return GenerateUtils.TIME.generate(format);
    }
}
