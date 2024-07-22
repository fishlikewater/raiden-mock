package io.github.fishlikewater.raiden.mock.service.generate;

import io.github.fishlikewater.raiden.generate.GenerateUtils;
import io.github.fishlikewater.raiden.mock.core.enums.DataTypeEnum;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

/**
 * {@code DateTimeGenerate}
 *
 * @author zhangxiang
 * @version 1.0.0
 * @since 2024/07/22
 */
@Service
public class DateTimeGenerate implements Generate {

    @Override
    public boolean support(DataTypeEnum type) {
        return type == DataTypeEnum.DATETIME;
    }

    @Override
    public Object generate(String expression) {
        String format = this.getExpression(expression);
        if (StringUtils.isBlank(format)) {
            return GenerateUtils.DATETIME.generate();
        }
        return GenerateUtils.DATETIME.generate(format);
    }
}
