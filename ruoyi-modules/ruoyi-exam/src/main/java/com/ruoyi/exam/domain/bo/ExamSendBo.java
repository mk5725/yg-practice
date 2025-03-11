package com.ruoyi.exam.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import java.math.BigDecimal;
import java.util.Date;
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
     * 试卷ID（关联 exam_paper.id）
     */
    @NotNull(message = "试卷ID（关联 exam_paper.id）不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long paperId;

    /**
     * 考生ID(关联 user.id)
     */
    @NotNull(message = "考生ID(关联 user.id)不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 考试开始时间
     */
    @NotNull(message = "考试开始时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date startTime;

    /**
     * 考试结束时间
     */
    @NotNull(message = "考试结束时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date endTime;

    /**
     * 考试状态（0-未开始，1-进行中，2-已完成, 3 - 弃考）
     */
    @NotNull(message = "考试状态（0-未开始，1-进行中，2-已完成, 3 - 弃考）不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer status;

    /**
     * 得分
     */
    @NotNull(message = "得分不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal score;

    /**
     * 提交时间（为空则表示未提交）
     */
    @NotNull(message = "提交时间（为空则表示未提交）不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date submitTime;


}
