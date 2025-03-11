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
 * 成绩统计视图对象
 *
 * @author zkm
 * @date  2025-03
 */
@Data
@ExcelIgnoreUnannotated
public class ExamCountVo implements Serializable {

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
     * 平均分
     */
    @ExcelProperty(value = "平均分")
    private BigDecimal avgScore;

    /**
     * 最高分
     */
    @ExcelProperty(value = "最高分")
    private BigDecimal maxScore;

    /**
     * 最低分
     */
    @ExcelProperty(value = "最低分")
    private BigDecimal minScore;

    /**
     * 及格率
     */
    @ExcelProperty(value = "及格率")
    private BigDecimal passRate;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;


}
