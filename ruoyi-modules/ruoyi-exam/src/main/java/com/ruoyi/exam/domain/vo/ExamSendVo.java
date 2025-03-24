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
 * 试卷发放记录视图对象
 *
 * @author zkm
 * @date  2025-03
 */
@Data
@ExcelIgnoreUnannotated
public class ExamSendVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ExcelProperty(value = "主键ID")
    private Long id;

    /**
     * 试卷ID
     */
    @ExcelProperty(value = "试卷ID", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "关=联,e=xam_paper.id")
    private Long paperId;


    /**
     * 试卷名称
     */
    @ExcelProperty(value = "试卷名称")
    private String paperName;


    /**
     * 试卷总分
     */
    @ExcelProperty(value = "试卷总分")
    private BigDecimal totalScore;

    /**
     * 试卷题目数量
     */
    @ExcelProperty(value = "题目数量")
    private Long totalQuestion;

    /**
     * 试卷及格
     */
    @ExcelProperty(value = "试卷及格")
    private BigDecimal passScore;


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

    /**
     * 考试时长
     */
    @ExcelProperty(value = "考试时长", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "分钟")
    private Integer duration;

    /**
     * 考试状态（0-未开始，1-进行中，2-已完成, 3 - 弃考）
     */
    @ExcelProperty(value = "考试状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=-未开始，1-进行中，2-已完成,,3=,-=,弃=考")
    private Integer status;

    /**
     * 考试记录
     */
    List<ExamRecordVo> recordList = new ArrayList<>();


    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;


}
