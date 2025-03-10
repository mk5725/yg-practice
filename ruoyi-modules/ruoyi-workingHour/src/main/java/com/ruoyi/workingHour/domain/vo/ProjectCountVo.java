package com.ruoyi.workingHour.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.excel.annotation.ExcelDictFormat;
import com.ruoyi.common.excel.convert.ExcelDictConvert;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


/**
 * 项目统计视图对象
 */
@Data
@ExcelIgnoreUnannotated
public class ProjectCountVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 项目ID
     */
    @ExcelProperty(value = "项目ID")
    private Long id;

    /**
     * 项目编号
     */
    @ExcelProperty(value = "项目编号")
    private String projectNo;

    /**
     * 项目名称
     */
    @ExcelProperty(value = "项目名称")
    private String projectName;

    /**
     * 项目状态（0: 新建, 1: 进行中, 2: 完成）
     */
    @ExcelProperty(value = "项目状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=:,新=建,,1=:,进=行中,,2=:,完=成")
    private String status;


    /**
     * 预计工时
     */
    @ExcelProperty(value = "预计工时")
    private BigDecimal expectedHour;

    /**
     * 总工时
     */
    @ExcelProperty(value = "总工时")
    private BigDecimal countHour;

    /**
     *  填报人数
     */
    @ExcelProperty(value = "填报人数")
    private Long countFillPeople;

    /**
     *  创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;


    /**
     *  填报人ID集合
     */
    @ExcelProperty(value = "填报人集合")
    private Set<Long> fillPeopleIds;

}
