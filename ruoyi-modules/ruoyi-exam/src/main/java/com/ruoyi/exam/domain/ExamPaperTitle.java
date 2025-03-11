package com.ruoyi.exam.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.web.domain.LsBaseEntity;

/**
 * 试卷标题对象 exam_paper_title
 *
 * @author zkm
 * @date 2025-03-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("exam_paper_title")
public class ExamPaperTitle extends LsBaseEntity {

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
     * 标题名称（如：单选题、多选题、判断题）
     */
    private String titleName;
    /**
     * 标题排序（显示顺序）
     */
    private Long orderNum;
    /**
     * 创建人ID
     */
    private Long createUserId;
    /**
     * 更新人ID
     */
    private Long updateUserId;
    /**
     * 删除标识（0：未删除，2：已删除）
     */
    @TableLogic
    private String delFlag;

}
