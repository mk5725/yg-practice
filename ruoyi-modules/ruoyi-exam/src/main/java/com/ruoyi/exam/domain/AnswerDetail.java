package com.ruoyi.exam.domain;

import com.baomidou.mybatisplus.annotation.*;
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
public class AnswerDetail extends LsBaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 试卷ID（关联 exam_paper.id）
     */
    private Long paperId;
    /**
     * 答题人ID（关联 user.id）
     */
    private Long userId;
    /**
     * 考试发放记录ID（关联 exam_send.id）
     */
    private Long examSendId;
    /**
     * 题目ID（关联 question.id）
     */
    private Long questionId;
    /**
     * 考生答题内容
     */
    private String userAnswer;
    /**
     * 是否正确（0-错误 1-正确）（适用于自动判分的题型）
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
     * 批改人ID（系统自动判分为null）
     */
    private Long reviewerUserId;
    /**
     * 批改时间
     */
    private Date reviewerTime;
    /**
     * 提交时间（为空则表示未提交）
     */
    private Date submitTime;
    /**
     * 删除标识（0：未删除，2：已删除）
     */
    @TableLogic
    private String delFlag;

}
