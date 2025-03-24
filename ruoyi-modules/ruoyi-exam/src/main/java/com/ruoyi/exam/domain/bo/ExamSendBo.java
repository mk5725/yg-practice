package com.ruoyi.exam.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.ArrayList;
import java.util.Date;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.core.web.domain.LsBaseEntity;

/**
 * 试卷发放记录业务对象
 *
 * @author zkm
 * @date  2025-03
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ExamSendBo extends LsBaseEntity {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 试卷ID
     */
    @NotNull(message = "试卷不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long paperId;

    /**
     * 试卷名称
     */
    private String paperName;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 考生考试状态
     */
    private Integer recordStatus;

    /**
     * 考生 ID 集合
     */
    @Size(min = 1, message = "至少选择一位考生",  groups = { AddGroup.class, EditGroup.class })
    private List<Long> userIds = new ArrayList<>();

    /**
     * 考试开始时间
     */
    @NotNull(message = "考试开始时间不能为空", groups = { AddGroup.class, EditGroup.class })
    @Future(message = "考试开始时间必须是未来时间")
    private Date startTime;

    /**
     * 考试结束时间
     */
    @Future(message = "考试结束时间必须是未来时间")
    @NotNull(message = "考试结束时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date endTime;

    /**
     * 考试时长（分钟）
     */
    @NotNull(message = "考试时长不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer duration;

    /**
     * 考试状态
     */
    private Integer status;

    /**
     * 提交时间
     */
    private Date submitTime;
}
