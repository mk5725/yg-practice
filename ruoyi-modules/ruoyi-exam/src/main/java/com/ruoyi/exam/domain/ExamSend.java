package com.ruoyi.exam.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import java.math.BigDecimal;
import java.util.Date;
import com.ruoyi.common.core.web.domain.LsBaseEntity;

/**
 * 试卷发放记录对象 exam_send
 *
 * @author zkm
 * @date  2025-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("exam_send")
public class ExamSend extends LsBaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 试卷ID（关联 exam_paper.id）
     */
    private Long paperId;
    /**
     * 考生ID(关联 user.id)
     */
    private Long userId;
    /**
     * 考试开始时间
     */
    private Date startTime;
    /**
     * 考试结束时间
     */
    private Date endTime;
    /**
     * 考试状态（0-未开始，1-进行中，2-已完成, 3 - 弃考）
     */
    private Integer status;
    /**
     * 得分
     */
    private BigDecimal score;
    /**
     * 提交时间（为空则表示未提交）
     */
    private Date submitTime;
    /**
     * 删除标识（0：未删除，2：已删除）
     */
    @TableLogic
    private String delFlag;

}
