package com.ruoyi.exam.common;

import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.exam.domain.dto.QuestionOptions;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestionOptionsTypeHandler extends AbstractJsonTypeHandler<List<QuestionOptions>> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected List<QuestionOptions> parse(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<QuestionOptions>>() {});
        } catch (Exception e) {
            throw new RuntimeException("JSON 解析失败", e);
        }
    }

    @Override
    protected String toJson(List<QuestionOptions> obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("JSON 序列化失败", e);
        }
    }
}
