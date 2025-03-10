package com.ruoyi.workingHour.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.system.api.domain.SysUser;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 项目成员视图对象
 */
@Data
@ExcelIgnoreUnannotated
public class ProjectMembersVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 项目成员ID
     */
    @ExcelProperty(value = "项目成员ID")
    private Long id;

    /**
     * 用户ID
     */
    @ExcelProperty(value = "用户ID")
    private Long userId;

    /**
     * 用户名
     */
    @ExcelProperty(value = "用户名称")
    private String userName;

    /**
     * 用户手机号
     */
    @ExcelProperty(value = "用户手机号")
    private String phonenumber;

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
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 创建人name
     */
    @ExcelProperty(value = "创建人name")
    private String createUserName;

}
