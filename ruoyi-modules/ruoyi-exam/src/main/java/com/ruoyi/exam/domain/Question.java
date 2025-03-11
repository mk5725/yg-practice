package com.ruoyi.exam.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.web.domain.LsBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 题目对象 question
 *
 * @author zkm
 * @date  2025-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("question")
public class Question extends LsBaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 题目类型ID
     */
    private Long questionTypeId;
    /**
     * 题目类型编码（1-单选, 2-多选, 3-判断, 4-填空, 5-简答, 6-其他）
     */
    private Integer questionTypeCode;
    /**
     * 题干内容
     */
    private String questionText;
    /**
     * 选项（JSON格式）
     */
    private String options;
    /**
     * 答案（填空/简答存文本，选择题存索引）
     */
    private String answer;
    /**
     * 解析
     */
    private String analysis;
    /**
     * 题目分值
     */
    private BigDecimal score;
    /**
     * 删除标识（0：未删除，2：已删除）
     */
    @TableLogic
    private String delFlag;

}
