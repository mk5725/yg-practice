package com.ruoyi.exam.domain.bo;

import com.fasterxml.jackson.databind.JsonNode;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.web.domain.LsBaseEntity;
import com.ruoyi.exam.domain.dto.ExamPaperData;
import com.ruoyi.exam.domain.dto.QuestionFillBlank;
import com.ruoyi.exam.domain.dto.QuestionOptions;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * 题目业务对象
 *
 * @author zkm
 * @date  2025-03
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionBo extends LsBaseEntity {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 题目类型ID
     */
    @NotNull(message = "题目类型ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long questionTypeId;

    /**
     * 题目类型编码（1-单选, 2-多选, 3-判断, 4-填空, 5-简答, 6-其他）
     */
    @NotNull(message = "题目类型编码不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer questionTypeCode;

    /**
     * 题干内容
     */
    @NotBlank(message = "题干内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String questionText;

    /**
     * 选项 JSON 格式
     */
    private JsonNode options;

    /**
     * 选项对象
     */
    private List<QuestionOptions> optionList;

    /**
     * 答案文本（填空/简答存文本，选择题存索引）
     */
     private String answer;

    /**
     * 填空数量
     */
    private Integer answerCount;

    /**
     * 解析
     */
    private String analysis;

    /**
     * 题目分值
     */
    @NotNull(message = "题目分值不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal score;
}
