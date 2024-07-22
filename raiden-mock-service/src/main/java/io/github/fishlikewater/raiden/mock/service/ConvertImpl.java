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
package io.github.fishlikewater.raiden.mock.service;

import io.github.fishlikewater.raiden.core.ObjectUtils;
import io.github.fishlikewater.raiden.mock.core.enums.DataTypeEnum;
import io.github.fishlikewater.raiden.mock.service.generate.Generate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@code ConvertImpl}
 *
 * @author zhangxiang
 * @version 1.0.0
 * @since 2024/07/22
 */
@Service
public class ConvertImpl implements Convert {

    private static final Pattern PATTERN = Pattern.compile("(@\\w+@(\\{.*?})?)");
    private final List<Generate> generates;

    public ConvertImpl(List<Generate> generates) {
        this.generates = generates;
    }

    @Override
    public Object convert(String expression) {
        Matcher matcher = PATTERN.matcher(expression);
        while (matcher.find()) {
            String express = matcher.group();
            DataTypeEnum dataType = this.getDataType(express);
            if (ObjectUtils.isNullOrEmpty(dataType)) {
                return expression;
            }
            for (Generate generate : generates) {
                if (!generate.support(dataType)) {
                    continue;
                }
                Object generated = generate.generate(expression);
                if (ObjectUtils.equals(expression, express)) {
                    return generated;
                }
                expression = expression.replace(express, generated.toString());
                break;
            }
        }
        return expression;
    }

    public static void main(String[] args) {
        String string = "@name@{20,30}";
        Matcher matcher = PATTERN.matcher(string);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}
