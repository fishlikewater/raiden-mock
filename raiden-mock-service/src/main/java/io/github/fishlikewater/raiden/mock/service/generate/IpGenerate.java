package io.github.fishlikewater.raiden.mock.service.generate;

import io.github.fishlikewater.raiden.generate.GenerateUtils;
import io.github.fishlikewater.raiden.mock.core.enums.DataTypeEnum;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

/**
 * {@code IpGenerate}
 *
 * @author zhangxiang
 * @version 1.0.0
 * @since 2024/07/22
 */
@Service
public class IpGenerate implements Generate {

    @Override
    public boolean support(DataTypeEnum type) {
        return type == DataTypeEnum.IP;
    }

    @Override
    public Object generate(String expression) {
        String str = this.getExpression(expression);
        if (StringUtils.isBlank(str)) {
            return GenerateUtils.IP.generate();
        }
        String[] split = str.split(",");
        if (split.length > 1) {
            String ip1 = split[0].trim();
            String ip2 = split[1].trim();
            return this.generateIp(ip1, ip2);
        }
        return GenerateUtils.IP.generate();
    }

    private Object generateIp(String ip1, String ip2) {
        return GenerateUtils.IP.generate();
    }
}
