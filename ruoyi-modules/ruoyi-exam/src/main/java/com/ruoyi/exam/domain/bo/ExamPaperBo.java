package com.ruoyi.exam.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.exam.domain.dto.ExamPaperData;
import com.ruoyi.exam.domain.vo.ExamPaperTitleVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.*;

import java.util.ArrayList;
import java.util.Date;

import java.math.BigDecimal;
import java.util.List;

import com.ruoyi.common.core.web.domain.LsBaseEntity;

/**
 * 试卷业务对象
 *
 * @author zkm
 * @date  2025-03
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ExamPaperBo extends LsBaseEntity {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 考试记录ID
     */
    private Long recordId;

    /**
     * 试卷名称
     */
    @NotBlank(message = "试卷名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String paperName;

    /**
     * 考试类型
     */
    @NotBlank(message = "考试类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String paperType;

    /**
     * 考试时长（分钟）
     */
    @NotNull(message = "考试时长不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer duration;

    /**
     * 试卷总分
     */
    @NotNull(message = "试卷总分不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal totalScore;

    /**
     * 及格分数
     */
    @NotNull(message = "及格分数不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal passScore;

    /**
     * 试卷标题 - 题目列表数据
     */
    @Valid
    @Size( min = 1, message = "至少添加一个标题",  groups = { AddGroup.class, EditGroup.class })
    private List<ExamPaperTitleBo> paperTitleList;
}
