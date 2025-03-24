package com.ruoyi.exam.domain.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.exam.domain.Question;
import com.ruoyi.exam.domain.dto.QuestionDto;
import com.ruoyi.exam.domain.vo.QuestionVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
     * 试卷ID
     */
    private Long paperId;

    /**
     * 标题名称
     */
    @NotBlank(message = "标题名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String titleName;

    /**
     * 标题排序（显示顺序）
     */
    private Integer orderNum;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 题目列表
     */
    @Valid
    @Size( min = 1, message = "至少添加一个题目",  groups = { AddGroup.class, EditGroup.class })
    private List<QuestionBo> questionList = new ArrayList<>();
}
