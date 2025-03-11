package com.ruoyi.exam.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.ruoyi.common.core.web.domain.LsBaseEntity;

/**
 * 题目类型对象 question_type
 *
 * @author zkm
 * @date 2025-03-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("question_type")
public class QuestionType extends LsBaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 题目类型ID
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 类型编码（（1-单选, 2-多选, 3-判断, 4-填空, 5-简答 ...）
     */
    private Long typeCode;
    /**
     * 类型名称（单选题、多选题、...）
     */
    private String typeName;
    /**
     * 创建人ID
     */
    private Long createUserId;
    /**
     * 更新人ID
     */
    private Long updateUserId;
    /**
     * 删除标识（0：未删除，2：已删除）
     */
    @TableLogic
    private String delFlag;

}
