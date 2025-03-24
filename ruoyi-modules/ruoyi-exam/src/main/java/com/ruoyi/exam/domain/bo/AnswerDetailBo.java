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
     * 试卷ID
     */
    @NotNull(message = "试卷ID 不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long paperId;

    /**
     * 答题人ID
     */
    @NotNull(message = "答题人ID 不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 考试发放记录ID
     */
    @NotNull(message = "考试发放记录I 不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long examSendId;

    /**
     * 考试记录ID
     */
    private Long recordId;

    /**
     * 题目ID
     */
    @NotNull(message = "题目ID 不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long questionId;

    /**
     * 考生答题内容
     */
    @NotBlank(message = "考生答题内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String userAnswer;

    /**
     * 是否正确（0-错误 1-正确）
     */
    private Integer okFlag;

    /**
     * 得分
     */
    private BigDecimal score;


}
