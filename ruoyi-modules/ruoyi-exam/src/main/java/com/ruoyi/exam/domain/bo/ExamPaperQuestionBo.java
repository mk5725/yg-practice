package com.ruoyi.exam.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import java.math.BigDecimal;
import com.ruoyi.common.core.web.domain.LsBaseEntity;

/**
 * 试卷题目关联业务对象
 *
 * @author zkm
 * @date  2025-03
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ExamPaperQuestionBo extends LsBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 试卷ID
     */
    @NotNull(message = "试卷ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long paperId;

    /**
     * 题目ID
     */
    @NotNull(message = "题目ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long questionId;

    /**
     * 标题ID
     */
    @NotNull(message = "标题ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long titleId;

    /**
     * 题目排序（升序）
     */
    @NotNull(message = "题目排序（升序）不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long orderSum;

    /**
     * 试卷版本
     */
    private Long paperVersion;


}
