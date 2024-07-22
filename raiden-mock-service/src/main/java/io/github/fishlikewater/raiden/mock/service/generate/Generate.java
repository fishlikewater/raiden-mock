package io.github.fishlikewater.raiden.mock.service.generate;

import io.github.fishlikewater.raiden.mock.core.enums.DataTypeEnum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@code Generate}
 *
 * @author zhangxiang
 * @version 1.0.0
 * @since 2024/07/22
 */
public interface Generate {

    Pattern EXPRESS = Pattern.compile("\\{(.*?)}");

    /**
     * 获取表达式
     *
     * @param expression 表达式
     * @return 表达式
     */
    default String getExpression(String expression) {
        Matcher matcher = EXPRESS.matcher(expression);
        if (!matcher.find()) {
            return null;
        }
        return matcher.group(1);
    }

    /**
     * 是否支持
     *
     * @param type 类型
     * @return 是否支持
     */
    boolean support(DataTypeEnum type);

    /**
     * 生成
     *
     * @param expression 表达式
     * @return 对象
     */
    Object generate(String expression);
}
