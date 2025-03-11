package com.ruoyi.exam.domain.vo;

import java.math.BigDecimal;
import java.util.Date;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.excel.annotation.ExcelDictFormat;
import com.ruoyi.common.excel.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;
import java.io.Serializable;


/**
 * 答题详情视图对象
 *
 * @author zkm
 * @date  2025-03
 */
@Data
@ExcelIgnoreUnannotated
public class AnswerDetailVo implements Serializable {

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
     * 答题人ID（关联 user.id）
     */
    @ExcelProperty(value = "答题人ID", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "关=联,u=ser.id")
    private Long userId;

    /**
     * 考试发放记录ID（关联 exam_send.id）
     */
    @ExcelProperty(value = "考试发放记录ID", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "关=联,e=xam_send.id")
    private Long examSendId;

    /**
     * 题目ID（关联 question.id）
     */
    @ExcelProperty(value = "题目ID", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "关=联,q=uestion.id")
    private Long questionId;

    /**
     * 考生答题内容
     */
    @ExcelProperty(value = "考生答题内容")
    private String userAnswer;

    /**
     * 是否正确（0-错误 1-正确）（适用于自动判分的题型）
     */
    @ExcelProperty(value = "是否正确", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=-错误,1=-正确")
    private Integer okFlag;

    /**
     * 得分
     */
    @ExcelProperty(value = "得分")
    private BigDecimal score;

    /**
     * 批改时间
     */
    @ExcelProperty(value = "批改时间")
    private Date reviewerTime;

    /**
     * 提交时间（为空则表示未提交）
     */
    @ExcelProperty(value = "提交时间", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "为=空则表示未提交")
    private Date submitTime;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;


}
