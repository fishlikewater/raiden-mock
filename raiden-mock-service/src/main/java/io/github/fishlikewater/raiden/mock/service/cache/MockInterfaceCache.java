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
package io.github.fishlikewater.raiden.mock.service.cache;

import cn.hutool.core.io.FileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.fishlikewater.raiden.config.ConfigParser;
import io.github.fishlikewater.raiden.config.ini.Ini;
import io.github.fishlikewater.raiden.config.ini.Section;
import io.github.fishlikewater.raiden.core.ObjectUtils;
import io.github.fishlikewater.raiden.core.StringUtils;
import io.github.fishlikewater.raiden.mock.core.model.RequestModel;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangxiang
 * @version 0.0.1
 * @since 2024/7/18
 */
public class MockInterfaceCache {

    private final ObjectMapper objectMapper;

    private final ConfigParser configParser;

    public MockInterfaceCache(ObjectMapper objectMapper, ConfigParser configParser) {
        this.objectMapper = objectMapper;
        this.configParser = configParser;
    }

    private final PathMatcher pathMatcher = new AntPathMatcher();

    private final Map<String, Map<String, Object>> globalMap = new ConcurrentHashMap<>();

    private final Map<String, RequestModel> mock = new ConcurrentHashMap<>();

    public Map<String, Map<String, Object>> getGlobal() {
        return this.globalMap;
    }

    public void addMock(String key, RequestModel requestModel) {
        mock.put(key, requestModel);
    }

    public void addGlobal(String scope, String key, Object value) {
        Map<String, Object> scopeMap = this.globalMap.get(scope);
        if (ObjectUtils.isNullOrEmpty(scopeMap)) {
            scopeMap = new ConcurrentHashMap<>(16);
            this.globalMap.put(scope, scopeMap);
        }
        scopeMap.put(key, value);
    }

    public void addGlobal(String scope, Map<String, Object> pairs) {
        Map<String, Object> map = globalMap.get(scope);
        if (ObjectUtils.isNullOrEmpty(map)) {
            map = new ConcurrentHashMap<>(16);
            this.globalMap.put(scope, map);
        }
        map.putAll(pairs);
    }

    public Optional<RequestModel> tryAcquireMock(String uri) {
        for (Map.Entry<String, RequestModel> entry : mock.entrySet()) {
            if (pathMatcher.match(entry.getKey(), uri)) {
                return Optional.of(entry.getValue());
            }
        }
        return Optional.empty();
    }

    public Object tryAcquireGlobal(String scope, String key) {
        Map<String, Object> objectMap = this.globalMap.get(scope);
        if (ObjectUtils.isNullOrEmpty(objectMap)) {
            return null;
        }
        return objectMap.get(key);
    }

    public void load() throws Exception {
        this.mock.clear();
        this.globalMap.clear();
        List<File> requests = FileUtil.loopFiles("request", file -> {return file.getName().endsWith(".ini");});
        for (File file : requests) {
            String fileName = file.getName();
            Ini ini = configParser.readIni(file);
            List<Section> sections = ini.getSections();
            for (Section section : sections) {
                String defaultStr = "default";
                if (section.getSectionName().equals(defaultStr)) {
                    this.addGlobal(fileName, section.getPairs());
                    continue;
                }
                String jsonString = objectMapper.writeValueAsString(section.getPairs());
                RequestModel requestModel = objectMapper.readValue(jsonString, RequestModel.class);
                if (StringUtils.isBlank(requestModel.getUrl())) {
                    requestModel.setUrl(section.getSectionName());
                }
                requestModel.setFileName(fileName);
                this.addMock(section.getSectionName(), requestModel);
            }
        }
    }
}
