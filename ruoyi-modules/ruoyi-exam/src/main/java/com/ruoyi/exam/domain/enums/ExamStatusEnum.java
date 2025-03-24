package com.ruoyi.exam.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 考试状态枚举
 */
@Getter
@AllArgsConstructor
public enum ExamStatusEnum {

    PENDING(0, "待考试"),
    IN_PROGRESS(1, "考试中"),
    PENDING_REVIEW(2, "待批改"),
    COMPLETED(3, "已完成");

    private final int code;
    private final String description;

    /**
     * 根据 code 获取枚举
     */
    public static ExamStatusEnum getByCode(int code) {
        for (ExamStatusEnum status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }
}
