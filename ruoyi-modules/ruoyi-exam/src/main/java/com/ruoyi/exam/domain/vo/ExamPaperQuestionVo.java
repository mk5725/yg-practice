package com.ruoyi.exam.domain.vo;

import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.excel.annotation.ExcelDictFormat;
import com.ruoyi.common.excel.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;
import java.io.Serializable;


/**
 * 试卷题目关联视图对象
 *
 * @author zkm
 * @date  2025-03
 */
@Data
@ExcelIgnoreUnannotated
public class ExamPaperQuestionVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @ExcelProperty(value = "主键ID")
    private Long id;

    /**
     * 试卷ID（关联 exam_paper.id）
     */
    @ExcelProperty(value = "试卷ID", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "关=联,e=xam_paper.id")
    private Long paperId;

    /**
     * 题目ID（关联 question.id）
     */
    @ExcelProperty(value = "题目ID", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "关=联,q=uestion.id")
    private Long questionId;

    /**
     * 标题ID（关联 exam_paper_title.id）
     */
    @ExcelProperty(value = "标题ID", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "关=联,e=xam_paper_title.id")
    private Long titleId;

    /**
     * 该题在试卷中的分值
     */
    @ExcelProperty(value = "该题在试卷中的分值")
    private BigDecimal score;

    /**
     * 题目排序（升序）
     */
    @ExcelProperty(value = "题目排序", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "升=序")
    private Long orderSum;

    /**
     * 试卷版本
     */
    @ExcelProperty(value = "试卷版本")
    private Long paperVersion;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;

}
