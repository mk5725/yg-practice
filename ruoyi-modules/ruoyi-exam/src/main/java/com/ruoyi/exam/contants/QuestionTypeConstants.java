package com.ruoyi.exam.contants;

/**
 * @author zkm
 */
public class QuestionTypeConstants {
    public static final Integer RADIO = 1;  // 单选题
    public static final Integer MULTIPLE = 2; // 多选题
    public static final Integer TRUE_FALSE = 3; // 判断题
    public static final Integer FILL_BLANK = 4; // 填空题
    public static final Integer SHORT_ANSWER = 5; // 简答题

    /**
     * 判断是否为单个答案的题型
     * @param code 题目类型代码
     * @return 是否为单个答案
     */
    public static boolean isSingleAnswer(int code){
        return code == RADIO || code == TRUE_FALSE || code == SHORT_ANSWER;
    }
}
