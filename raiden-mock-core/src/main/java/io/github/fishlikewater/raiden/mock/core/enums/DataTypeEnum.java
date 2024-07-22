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
package io.github.fishlikewater.raiden.mock.core.enums;

import lombok.Getter;

/**
 * {@code DataTypeEnum}
 *
 * @author zhangxiang
 * @version 1.0.0
 * @since 2024/07/16
 */
@Getter
public enum DataTypeEnum {

    // 类型
    REQUEST("请求参数", "request"),
    AGE("年龄", "age"),
    NAME("名称", "name"),
    MOBILE("手机号", "mobile"),
    EMAIL("邮箱", "email"),
    ID_CARD("身份证", "idCard"),
    IP("IP", "ip"),
    DATE("日期", "date"),
    TIME("时间", "time"),
    DATETIME("日期时间", "datetime"),
    TIMESTAMP("时间戳", "timestamp"),
    TIMESTAMP_CURRENT("时间戳", "timestamp_c"),
    RANDOM("随机", "random"),
    NUMBER("数字", "number"),
    ADDRESS("地址", "address"),
    ;

    private final String desc;

    private final String value;

    DataTypeEnum(String desc, String value) {
        this.desc = desc;
        this.value = value;
    }
    public static DataTypeEnum getByValue(String value) {
        for (DataTypeEnum dataTypeEnum : DataTypeEnum.values()) {
            if (dataTypeEnum.getValue().equals(value)) {
                return dataTypeEnum;
            }
        }
        throw  new IllegalArgumentException("not support this value");
    }
}
