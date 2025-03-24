package com.ruoyi.exam.domain.dto;

import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.exam.domain.Question;
import com.ruoyi.exam.domain.bo.QuestionBo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 题目标题业务对象
 *
 * @author zkm
 * @date  2025-03
 */

@Data
public class ExamPaperData {

    /**
     * 试卷标题主键Id
     */
    private Long id;

    /**
     * 标题排序索引
     */
    private Integer orderNum;

    /**
     * 标题名称
     */
    private String titleName;

    /**
     * 题目列表
     */
    private List<QuestionDto> questionList = new ArrayList<>();

}
