package com.ruoyi.exam.domain.dto;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 题目选项业务对象
 *
 * @author zkm
 * @date  2025-03
 */

@Data
public class QuestionOptions {

    /**
     * 选项 （A、B、C ...）
     */
    @NotBlank(message = "选项不能为空", groups = { AddGroup.class, EditGroup.class })
    private String option;

    /**
     * 选项内容
     */
    @NotBlank(message = "选项内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String content;

}
