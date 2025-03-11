package com.ruoyi.exam.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import java.math.BigDecimal;
import com.ruoyi.common.core.web.domain.LsBaseEntity;

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
    @NotNull(message = "题目类型编码（1-单选, 2-多选, 3-判断, 4-填空, 5-简答, 6-其他）不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer questionTypeCode;

    /**
     * 题干内容
     */
    @NotBlank(message = "题干内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String questionText;

    /**
     * 选项（JSON格式）
     */
    @NotBlank(message = "选项（JSON格式）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String options;

    /**
     * 答案（填空/简答存文本，选择题存索引）
     */
    @NotBlank(message = "答案（填空/简答存文本，选择题存索引）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String answer;

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
