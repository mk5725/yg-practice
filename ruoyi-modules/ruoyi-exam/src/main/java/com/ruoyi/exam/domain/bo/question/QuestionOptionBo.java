package com.ruoyi.exam.domain.bo.question;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.exam.domain.bo.QuestionBo;
import com.ruoyi.exam.domain.dto.QuestionOptions;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 题目业务对象
 *
 * @author zkm
 * @date  2025-03
 */

@Data
public class QuestionOptionBo extends QuestionBo {

    /**
     * 选项
     */
    @NotEmpty(message = "选项列表不能为空", groups = { AddGroup.class, EditGroup.class })
    @Size(message = "至少包含两个选项，最多六个", min = 2, max = 6, groups = { AddGroup.class, EditGroup.class })
    @Valid
    private List<QuestionOptions> optionList;

}
