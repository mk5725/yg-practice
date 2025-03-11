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
 * 试卷视图对象
 *
 * @author zkm
 * @date  2025-03
 */
@Data
@ExcelIgnoreUnannotated
public class ExamPaperVo implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键ID
     */
    @ExcelProperty(value = "主键ID")
    private Long id;

    /**
     * 试卷名称
     */
    @ExcelProperty(value = "试卷名称")
    private String paperName;

    /**
     * 考试类型
     */
    @ExcelProperty(value = "考试类型")
    private String paperType;

    /**
     * 考试时长（分钟）
     */
    @ExcelProperty(value = "考试时长", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "分=钟")
    private Long duration;

    /**
     * 试卷总分
     */
    @ExcelProperty(value = "试卷总分")
    private BigDecimal totalScore;

    /**
     * 及格分数
     */
    @ExcelProperty(value = "及格分数")
    private BigDecimal passScore;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;


}
