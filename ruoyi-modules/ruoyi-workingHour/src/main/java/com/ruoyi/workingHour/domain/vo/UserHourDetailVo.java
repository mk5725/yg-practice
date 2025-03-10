package com.ruoyi.workingHour.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 用户工时详情视图对象
 */
@Data
@ExcelIgnoreUnannotated
public class UserHourDetailVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 工时记录ID
     */
    @ExcelProperty(value = "工时记录ID")
    private Long id;

    /**
     * 用户ID
     */
    @ExcelProperty(value = "用户ID")
    private Long userId;

    /**
     * 项目ID
     */
    @ExcelProperty(value = "项目ID")
    private Long projectId;

    /**
     * 项目名称
     */
    @ExcelProperty(value = "项目名称")
    private String projectName;

    /**
     * 星期
     */
    @ExcelProperty(value = "星期")
    private String week;

    /**
     * 工时
     */
    @ExcelProperty(value = "工时")
    private BigDecimal workHours;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String description;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 填报更新时间
     */
    @ExcelProperty(value = "更新时间")
    private Date updateTime;


}
