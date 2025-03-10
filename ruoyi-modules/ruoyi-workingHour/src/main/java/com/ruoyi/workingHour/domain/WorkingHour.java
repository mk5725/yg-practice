package com.ruoyi.workingHour.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 工时信息对象 working_hour
 *
 */
@Data
@TableName("working_hour")
public class WorkingHour implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 工时记录ID
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 项目ID
     */
    private Long projectId;
    /**
     * 工时
     */
    private BigDecimal workHours;
    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    /**
     * 删除标识（0：未删除，2：删除）
     */
    @TableLogic
    private String delFlag;

}
