/*
 * Copyright (c) 2024 zhangxiang (fishlikewater@126.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
