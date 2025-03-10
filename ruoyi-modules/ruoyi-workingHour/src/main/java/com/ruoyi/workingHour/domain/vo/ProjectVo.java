package com.ruoyi.workingHour.domain.vo;

import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.excel.annotation.ExcelDictFormat;
import com.ruoyi.common.excel.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;
import java.io.Serializable;


/**
 * 项目管理视图对象
 */
@Data
@ExcelIgnoreUnannotated
public class ProjectVo implements Serializable {

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
     * 项目简介
     */
    @ExcelProperty(value = "项目简介")
    private String description;

    /**
     * 项目经理ID
     */
    @ExcelProperty(value = "项目经理ID")
    private Long managerId;

    /**
     * 项目经理
     */
    @ExcelProperty(value = "项目经理")
    private String managerName;

    /**
     * 预计工时
     */
    @ExcelProperty(value = "预计工时")
    private BigDecimal expectedHour;

    /**
     * 创建人name
     */
    @ExcelProperty(value = "创建人name")
    private String createUserName;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 删除标识
     */
    @ExcelProperty(value = "删除标识")
    private String delFlag;


}
