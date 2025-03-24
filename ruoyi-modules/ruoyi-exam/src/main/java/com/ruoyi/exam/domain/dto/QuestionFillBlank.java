package com.ruoyi.exam.domain.dto;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 填空题目答案业务对象
 *
 * @author zkm
 * @date  2025-03
 */

@Data
public class QuestionFillBlank {

    /**
     * 答案位置索引
     */
    @NotBlank(message = "答案位置索引", groups = { AddGroup.class, EditGroup.class })
    private String index;

    /**
     * 答案内容
     */
    @NotBlank(message = "答案内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String content;

}
