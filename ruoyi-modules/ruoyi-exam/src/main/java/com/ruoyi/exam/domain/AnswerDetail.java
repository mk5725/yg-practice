package com.ruoyi.exam.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import java.math.BigDecimal;
import java.util.Date;
import com.ruoyi.common.core.web.domain.LsBaseEntity;

/**
 * 答题详情对象 answer_detail
 *
 * @author zkm
 * @date  2025-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("answer_detail")
@Builder
public class AnswerDetail extends LsBaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 试卷ID
     */
    private Long paperId;
    /**
     * 答题人ID
     */
    private Long userId;
    /**
     * 考试发放记录ID
     */
    private Long examSendId;
    /**
     * 题目ID
     */
    private Long questionId;
    /**
     * 考试记录ID
     */
    private Long recordId;
    /**
     * 考生答题内容
     */
    private String userAnswer;
    /**
     * 是否正确（0-错误 1-正确,2-待批改）
     */
    private Integer okFlag;
    /**
     * 得分
     */
    private BigDecimal score;
    /**
     * 答题版本
     */
    @Version
    private Long userAnswerVersion;
    /**
     * 提交时间
     */
    private Date submitTime;
    /**
     * 删除标识（0：未删除，2：已删除）
     */
    @TableLogic
    private String delFlag;

}
