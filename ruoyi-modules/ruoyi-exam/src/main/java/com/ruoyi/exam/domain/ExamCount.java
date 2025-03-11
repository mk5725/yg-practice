package com.ruoyi.exam.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import java.math.BigDecimal;
import com.ruoyi.common.core.web.domain.LsBaseEntity;

/**
 * 成绩统计对象 exam_count
 *
 * @author zkm
 * @date  2025-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("exam_count")
public class ExamCount extends LsBaseEntity {

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
     * 平均分
     */
    private BigDecimal avgScore;
    /**
     * 最高分
     */
    private BigDecimal maxScore;
    /**
     * 最低分
     */
    private BigDecimal minScore;
    /**
     * 及格率
     */
    private BigDecimal passRate;
    /**
     * 删除标识（0：未删除，2：已删除）
     */
    @TableLogic
    private String delFlag;

}
