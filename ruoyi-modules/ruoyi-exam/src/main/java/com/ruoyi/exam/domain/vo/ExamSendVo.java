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
     * 试卷ID（关联 exam_paper.id）
     */
    @ExcelProperty(value = "试卷ID", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "关=联,e=xam_paper.id")
    private Long paperId;

    /**
     * 考生ID(关联 user.id)
     */
    @ExcelProperty(value = "考生ID(关联 user.id)")
    private Long userId;

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
     * 考试状态（0-未开始，1-进行中，2-已完成, 3 - 弃考）
     */
    @ExcelProperty(value = "考试状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=-未开始，1-进行中，2-已完成,,3=,-=,弃=考")
    private Integer status;

    /**
     * 得分
     */
    @ExcelProperty(value = "得分")
    private BigDecimal score;

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
