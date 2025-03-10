package com.ruoyi.workingHour.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;


/**
 * 用户工时统计视图对象
 */
@Data
@ExcelIgnoreUnannotated
public class UserHourCountVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @ExcelProperty(value = "用户ID")
    private Long userId;

    /**
     * 用户名称
     */
    @ExcelProperty(value = "用户名称")
    private String userName;

    /**
     * 总工时
     */
    @ExcelProperty(value = "总工时")
    private BigDecimal countHour;

    /**
     * 填报项目集合
     */
    @ExcelProperty(value = "填报项目集合")
    private Set<String> projectList;

}
