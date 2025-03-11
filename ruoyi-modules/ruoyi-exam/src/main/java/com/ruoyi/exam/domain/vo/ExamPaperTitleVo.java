package com.ruoyi.exam.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.excel.annotation.ExcelDictFormat;
import com.ruoyi.common.excel.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;
import java.io.Serializable;


/**
 * 试卷标题视图对象
 *
 * @author zkm
 * @date 2025-03-11
 */
@Data
@ExcelIgnoreUnannotated
public class ExamPaperTitleVo implements Serializable {

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
     * 标题名称（如：单选题、多选题、判断题）
     */
    @ExcelProperty(value = "标题名称", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "如=：单选题、多选题、判断题")
    private String titleName;

    /**
     * 标题排序（显示顺序）
     */
    @ExcelProperty(value = "标题排序", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "显=示顺序")
    private Long orderNum;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;


}
