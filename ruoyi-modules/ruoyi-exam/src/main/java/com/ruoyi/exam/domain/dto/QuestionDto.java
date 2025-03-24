package com.ruoyi.exam.domain.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.web.domain.LsBaseEntity;
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
public class QuestionDto extends LsBaseEntity {


    /**
     * 试卷题目关联主键ID
     */
    private Long examPaperQuestionId;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 题目排序
     */
    private Long questionOrder;

    /**
     * 题目类型ID
     */
    private Long questionTypeId;

    /**
     * 题目类型编码
     */
    private Integer questionTypeCode;

    /**
     * 题干内容
     */
    private String questionText;

    /**
     * 题目分值
     */
    private BigDecimal score;

    /**
     * 选项
     */
    private String options;

    /**
     * 答案
     */
    private String answer;

    /**
     * 解析
     */
    private String analysis;
}
