package com.ruoyi.exam.common;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期格式化
 */
@Component
public class StringToDateConverter implements Converter<String, Date> {

    private final ExamProperties examProperties;

    public StringToDateConverter(ExamProperties examProperties) {
        this.examProperties = examProperties;
    }

    @Override
    public Date convert(String source) {
        SimpleDateFormat sdf = new SimpleDateFormat(examProperties.getFormat());
        try {
            return sdf.parse(source);
        } catch (ParseException e) {
            throw new IllegalArgumentException("日期格式化异常: " + examProperties.getFormat());
        }
    }
}
