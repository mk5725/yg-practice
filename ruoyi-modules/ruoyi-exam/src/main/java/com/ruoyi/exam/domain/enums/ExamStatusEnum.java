package com.ruoyi.exam.domain.enums;

/**
 * 试卷状态枚举
 * @author zkm
 */
public enum ExamStatusEnum {

    DRAFT(0, "草稿"),
    NEW(1, "新建"),
    ONGOING(2, "考试中"),
    FINISHED(3, "已结束"),
    TO_BE_REVIEWED(4, "待批改"),
    GRADED(5, "已批阅"),
    CANCELLED(6, "已取消");

    private final int code;
    private final String description;

    ExamStatusEnum(int code, String description) {
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
    public static ExamStatusEnum fromCode(int code) {
        for (ExamStatusEnum status : ExamStatusEnum.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("未知的试卷状态代码：" + code);
    }
}
