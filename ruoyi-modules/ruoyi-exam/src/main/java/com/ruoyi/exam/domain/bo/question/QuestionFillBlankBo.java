package com.ruoyi.exam.domain.bo.question;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.exam.domain.bo.QuestionBo;
import com.ruoyi.exam.domain.dto.QuestionFillBlank;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 填空题目答案业务对象
 *
 * @author zkm
 * @date  2025-03
 */

@Data
public class QuestionFillBlankBo extends QuestionBo {

    /**
     * 填空答案
     *
     */
    @NotEmpty(message = "填空答案不能为空", groups = { AddGroup.class, EditGroup.class })
    @Valid
    private List<QuestionFillBlank> fillBlankAnswer;

}
