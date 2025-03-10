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

import io.github.fishlikewater.raiden.core.constant.CommonConstants;
import io.github.fishlikewater.raiden.mock.core.enums.DataTypeEnum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@code Convert}
 * 表达式装换与解析
 *
 * @author zhangxiang
 * @version 1.0.0
 * @since 2024/07/22
 */
public interface Convert {

    Pattern PATTERN = Pattern.compile("@(\\w+)@");

    /**
     * 获取数据类型
     *
     * @param value 值
     * @return 类型
     */
    default DataTypeEnum getDataType(String value) {
        if (value.startsWith(CommonConstants.SYMBOL_EXPRESSION)) {
            return DataTypeEnum.REQUEST;
        }
        Matcher matcher = PATTERN.matcher(value);
        if (matcher.find()) {
            String group = matcher.group(1);
            return DataTypeEnum.getByValue(group);
        }
        return null;
    }

    /**
     * 表达式转对象
     *
     * @param expression 表达式
     * @return 对象
     */
    Object convert(String expression);
}
