package com.ruoyi.exam.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.web.domain.LsBaseEntity;

/**
 * 试卷标题业务对象
 *
 * @author zkm
 * @date 2025-03-11
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ExamPaperTitleBo extends LsBaseEntity {

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
     * 标题名称（如：单选题、多选题、判断题）
     */
    @NotBlank(message = "标题名称（如：单选题、多选题、判断题）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String titleName;

    /**
     * 标题排序（显示顺序）
     */
    @NotNull(message = "标题排序（显示顺序）不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long orderNum;


}
