package com.ruoyi.workingHour.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.core.web.domain.WhBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
/**
 * 项目管理对象 project
 *
 */
@Data
@TableName("project")
@EqualsAndHashCode(callSuper = true)
public class Project extends WhBaseEntity {

    /**
     * 项目ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 项目编号
     */
    private String projectNo;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目状态（0: 新建, 1: 进行中, 2: 完成）
     */
    private String status;

    /**
     * 项目简介
     */
    private String description;

    /**
     * 项目经理ID
     */
    private Long managerId;

    /**
     * 预计工时
     */
    private BigDecimal expectedHour;

    /**
     * 删除标识（0: 未删除, 2: 删除）
     */
    @TableLogic
    private String delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
