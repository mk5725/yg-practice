package com.ruoyi.exam.domain.enums;

import lombok.Getter;

/**
 * 题目类型枚举类
 * @author z
 */
@Getter
public enum QuestionTypeEnum {

    SINGLE(1, "单选题"),
    MULTIPLE(2, "多选题"),
    TRUE_FALSE(3, "判断题"),
    FILL_BLANK(4, "填空题"),
    SHORT_ANSWER(5, "简答题"),
    PROGRAMMING(6, "编程题"),
    DISCUSSION(7, "论述题");

    private final int code;
    private final String description;
    QuestionTypeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 根据 code 获取枚举对象
     */
    public static QuestionTypeEnum fromCode(int code) {
        for (QuestionTypeEnum type : QuestionTypeEnum.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("未知的题目类型代码：" + code);
    }

}
