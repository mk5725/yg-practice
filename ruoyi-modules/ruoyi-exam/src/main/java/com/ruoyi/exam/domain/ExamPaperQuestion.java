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
import com.ruoyi.common.core.web.domain.LsBaseEntity;

/**
 * 试卷题目关联对象 exam_paper_question
 *
 * @author zkm
 * @date  2025-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("exam_paper_question")
@Builder
public class ExamPaperQuestion extends LsBaseEntity {

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
     * 题目ID
     */
    private Long questionId;
    /**
     * 标题ID
     */
    private Long titleId;
    /**
     * 题目排序（升序）
     */
    private Integer orderSum;
    /**
     * 试卷版本
     */
    private Long paperVersion;
    /**
     * 删除标识（0：未删除，1：已删除）
     */
    @TableLogic
    private String delFlag;

}
