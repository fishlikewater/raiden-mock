package io.github.fishlikewater.raiden.mock.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.fishlikewater.raiden.core.Assert;
import io.github.fishlikewater.raiden.core.ObjectUtils;
import io.github.fishlikewater.raiden.core.RandomUtils;
import io.github.fishlikewater.raiden.core.StringUtils;
import io.github.fishlikewater.raiden.core.constant.CommonConstants;
import io.github.fishlikewater.raiden.core.model.Result;
import io.github.fishlikewater.raiden.generate.GenerateUtils;
import io.github.fishlikewater.raiden.mock.core.enums.DataTypeEnum;
import io.github.fishlikewater.raiden.mock.core.model.RequestModel;
import io.github.fishlikewater.raiden.mock.service.cache.MockInterfaceCache;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@code MockServiceImpl}
 *
 * @author zhangxiang
 * @version 1.0.0
 * @since 2024/07/16
 */
@Slf4j
@Service
@SuppressWarnings("all")
public class MockServiceImpl implements MockService {

    private final Pattern pattern = Pattern.compile("@(\\w+)@");
    private final Pattern pathPattern = Pattern.compile("\\{(\\w+)}");

    private final ObjectMapper objectMapper;

    private final MockInterfaceCache mockInterface;

    public MockServiceImpl(ObjectMapper objectMapper, MockInterfaceCache mockInterfaceCache) {
        this.objectMapper = objectMapper;
        this.mockInterface = mockInterfaceCache;
    }

    @Override
    public Object handleMock() throws Exception {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Assert.notNull(attributes, "attributes is null, but this is not allow");
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        String uri = request.getRequestURI();
        Optional<RequestModel> optional = mockInterface.tryAcquireMock(uri);
        if (optional.isPresent()) {
            RequestModel requestModel = optional.get();
            Map<String, Object> warpperMap = this.tryAcquireResponseWrapper(requestModel);
            String responseCode = requestModel.getResponseCode();
            this.addHeaders(response, requestModel.getResponseHeaders());
            Map<String, Object> map = this.resolveResponseBody(request, response, requestModel);
            if (ObjectUtils.isNullOrEmpty(warpperMap)) {
                return Result.of("success", responseCode, map);
            }
            return this.wrapperResult(warpperMap, responseCode, map);
        }
        return Result.of("failed", "00");
    }

    private Object wrapperResult(Map<String, Object> warpperMap, String responseCode, Map<String, Object> map) {
        Map<String, Object> returnMap = new HashMap<>(8);
        for (Map.Entry<String, Object> entry : warpperMap.entrySet()) {
            if (entry.getKey().equals("result")) {
                returnMap.put((String) warpperMap.get("result"), map);
                continue;
            }
            if (entry.getKey().equals("code")) {
                returnMap.put((String) warpperMap.get("code"), responseCode);
                continue;
            }
            if (entry.getKey().equals("message")) {
                returnMap.put((String) warpperMap.get("message"), "success");
                continue;
            }
            String value = (String) entry.getValue();
            DataTypeEnum dataType = this.getDataType(value);
            returnMap.put(entry.getKey(), this.tryGenerate(dataType));
        }

        return returnMap;
    }

    private Map<String, Object> tryAcquireResponseWrapper(RequestModel requestModel) throws JsonProcessingException {
        Map<String, Object> responseWrapper = requestModel.getResponseWrapper();
        if (ObjectUtils.isNullOrEmpty(responseWrapper)) {
            String str = (String) mockInterface.tryAcquireGlobal(requestModel.getFileName(), "response_wrapper");
            if (ObjectUtils.isNullOrEmpty(str)) {
                return null;
            }
            responseWrapper = objectMapper.readValue(str, new TypeReference<Map<String, Object>>() {});
        }
        return responseWrapper;
    }

    private Map<String, Object> resolveResponseBody(HttpServletRequest request,
                                                    HttpServletResponse response,
                                                    RequestModel requestModel) {
        Map<String, Object> responseBody = requestModel.getResponseBody();
        Map<String, Object> responseMap = new HashMap<>(16);
        responseBody.forEach((key, value) -> {
            if (value instanceof String str) {
                DataTypeEnum dataType = this.getDataType(str);
                if (Objects.isNull(dataType)) {
                    responseMap.put(key, str);
                    return;
                }
                if (dataType == DataTypeEnum.REQUEST) {
                    Object obj = this.tryRequestParams(request, requestModel, str);
                    responseMap.put(key, obj);
                } else {
                    Object generate = this.tryGenerate(dataType);
                    if (str.equals("@" + dataType.getValue() + "@")) {
                        responseMap.put(key, generate);
                    } else {
                        String replaced = str.replaceAll("@\\w+@", generate.toString());
                        responseMap.put(key, replaced);
                    }
                }
            }
            if (value instanceof Map map) {
                responseMap.put(key, this.resolveResponseBody(request, response, requestModel));
            }
        });
        return responseMap;
    }

    private Object tryGenerate(DataTypeEnum dataTypeEnum) {
        return switch (dataTypeEnum) {
            case RANDOM -> RandomUtils.randomNumberAndAlphabet(6);
            case AGE -> GenerateUtils.AGE.generate();
            case EMAIL -> GenerateUtils.EMAIL.generate();
            case MOBILE -> GenerateUtils.MOBILE_PHONE.generate();
            case NAME -> GenerateUtils.USER_NAME.generate();
            case ID_CARD -> GenerateUtils.ID_CARD.generate();
            case ADDRESS -> GenerateUtils.ADDRESS.generate().address();
            case IP -> GenerateUtils.IP.generate();
            case TIME -> GenerateUtils.TIME.generate();
            case TIMESTAMP -> GenerateUtils.TIMESTAMP.generate();
            case DATE -> GenerateUtils.DATE.generate();
            case DATETIME -> GenerateUtils.DATETIME.generate();
            case TIMESTAMP_CURRENT -> System.currentTimeMillis();
            case NUMBER -> RandomUtils.randomInt();
            default -> null;
        };
    }

    private Object tryRequestParams(HttpServletRequest request, RequestModel requestModel, String value) {
        String method = requestModel.getMethod();
        String url = requestModel.getUrl();
        String requestUri = request.getRequestURI();
        String field = value.replace(CommonConstants.SYMBOL_EXPRESSION, "");
        // 请求路径中参数
        boolean contain = StringUtils.contains(url, "{" + field + "}");
        if (contain) {
            String[] split = url.split("/");
            String[] requestUriSplit = requestUri.split("/");
            for (int i = 0; i < split.length; i++) {
                if (split[i].equals("{" + field + "}")) {
                    return requestUriSplit[i];
                }
            }
        }
        // 查询请求参数中数据
        String parameter = request.getParameter(field);
        if (StringUtils.isNotBlank(parameter)) {
            return parameter;
        }
        // 查询body 中的数据
        Map<String, Object> body = this.readBody(request);
        if (ObjectUtils.isNullOrEmpty(body)) {
            return value;
        }
        Object o = this.bodyPattern(body, field, "");
        if (ObjectUtils.isNotNullOrEmpty(o)) {
            return o;
        }
        return value;
    }

    private Map<String, Object> readBody(HttpServletRequest request) {
        if (request.getContentType().contains("application/json")) {
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                return objectMapper.readValue(sb.toString(), new TypeReference<Map<String, Object>>() {});
            } catch (Exception e) {
                log.error("read body error", e);
            }
        }
        return null;
    }

    private Object bodyPattern(Map<String, Object> body, String field, String initKey) {
        for (Map.Entry<String, Object> entry : body.entrySet()) {
            String key;
            if (StringUtils.isBlank(initKey)) {
                key = entry.getKey();
            } else {
                key = StringUtils.format("{}.{}", initKey, entry.getKey());
            }
            if (key.equals(field)) {
                return entry.getValue();
            }
            if (field.startsWith(key) && entry.getValue() instanceof Map map) {
                return bodyPattern(map, field, key);
            }
        }
        return null;
    }

    private void addHeaders(HttpServletResponse response, String[] responseHeaders) {
        if (ObjectUtils.isNullOrEmpty(responseHeaders)) {
            return;
        }
        for (String header : responseHeaders) {
            String[] split = header.split(":");
            response.addHeader(split[0], split[1]);
        }
    }

    private DataTypeEnum getDataType(String value) {
        if (value.startsWith(CommonConstants.SYMBOL_EXPRESSION)) {
            return DataTypeEnum.REQUEST;
        }
        Matcher matcher = this.pattern.matcher(value);
        if (matcher.find()) {
            String group = matcher.group(1);
            return DataTypeEnum.getByValue(group);
        }
        return null;
    }
}
