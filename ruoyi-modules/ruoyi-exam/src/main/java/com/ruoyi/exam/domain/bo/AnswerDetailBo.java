package com.ruoyi.exam.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import java.math.BigDecimal;
import java.util.Date;
import com.ruoyi.common.core.web.domain.LsBaseEntity;

/**
 * 答题详情业务对象
 *
 * @author zkm
 * @date  2025-03
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class AnswerDetailBo extends LsBaseEntity {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 试卷ID（关联 exam_paper.id）
     */
    @NotNull(message = "试卷ID（关联 exam_paper.id）不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long paperId;

    /**
     * 答题人ID（关联 user.id）
     */
    @NotNull(message = "答题人ID（关联 user.id）不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 考试发放记录ID（关联 exam_send.id）
     */
    @NotNull(message = "考试发放记录ID（关联 exam_send.id）不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long examSendId;

    /**
     * 题目ID（关联 question.id）
     */
    @NotNull(message = "题目ID（关联 question.id）不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long questionId;

    /**
     * 考生答题内容
     */
    @NotBlank(message = "考生答题内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String userAnswer;

    /**
     * 是否正确（0-错误 1-正确）（适用于自动判分的题型）
     */
    private Integer okFlag;

    /**
     * 得分
     */
    private BigDecimal score;


}
