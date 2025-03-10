package com.ruoyi.workingHour.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 项目填报详情视图
 *
 * @author ruoyi
 * @date 2025-02-22
 */
@Data
@ExcelIgnoreUnannotated
public class ProjectFillDetailVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 项目ID
     */
    @ExcelProperty(value = "项目ID")
    private Long id;

    /**
     * 项目名称
     */
    @ExcelProperty(value = "项目名称")
    private String projectName;

    /**
     * 填报人Id
     */
    @ExcelProperty(value = "填报人Id")
    private Long fillUserId;

    /**
     * 填报人
     */
    @ExcelProperty(value = "填报人")
    private String fillUserName;


    /**
     * 填报工时
     */
    @ExcelProperty(value = "填报工时")
    private BigDecimal workHours;

    /**
     * 填报日期
     */
    @ExcelProperty(value = "填报日期")
    private Date fillDate;


}
