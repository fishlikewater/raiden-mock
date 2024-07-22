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
