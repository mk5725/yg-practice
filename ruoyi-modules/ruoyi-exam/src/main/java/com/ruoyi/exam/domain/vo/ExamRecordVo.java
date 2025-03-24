package com.ruoyi.exam.domain.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.excel.annotation.ExcelDictFormat;
import com.ruoyi.common.excel.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.util.List;


/**
 * 考试记录信息视图对象
 *
 * @author zkm
 * @date 2025-03-15
 */
@Data
@ExcelIgnoreUnannotated
public class ExamRecordVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ExcelProperty(value = "主键ID")
    private Long id;

    /**
     * 发放记录ID
     */
    @ExcelProperty(value = "发放记录ID")
    private Long sendId;

    /**
     * 考生ID
     */
    @ExcelProperty(value = "考生ID")
    private Long userId;

    /**
     * 考生名称
     */
    @ExcelProperty(value = "考生名称")
    private String userName;

    /**
     * 试卷ID
     */
    private Long paperId;

    /**
     * 试卷名称
     */
    @ExcelProperty(value = "试卷名称")
    private String paperName;

    /**
     * 考生考试状态
     */
    @ExcelProperty(value = "考试状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0,-=,考")
    private Integer recordStatus;

    /**
     * 得分
     */
    @ExcelProperty(value = "得分")
    private BigDecimal score;

    /**
     * 试卷总分
     */
    @ExcelProperty(value = "试卷总分")
    private BigDecimal totalScore;

    /**
     * 答对题目数量
     */
    @ExcelProperty(value = "题目数量")
    private Integer totalOkQuestion;

    /**
     * 试卷题目数量
     */
    @ExcelProperty(value = "题目数量")
    private Integer totalQuestion;

    /**
     * 批改人ID
     */
    private Long reviewerUserId;

    /**
     * 批改人ID
     */
    private String reviewerUserName;

    /**
     * 批改时间
     */
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

    /**
     * 考试开始时间
     */
    @ExcelProperty(value = "考试开始时间")
    private Date startTime;

    /**
     * 考试结束时间
     */
    @ExcelProperty(value = "考试结束时间")
    private Date endTime;

}
