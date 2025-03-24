package com.ruoyi.exam.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
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
 * 考试记录信息对象 exam_record
 *
 * @author zkm
 * @date 2025-03-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("exam_record")
@Builder
public class ExamRecord extends LsBaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 发放记录ID
     */
    private Long sendId;
    /**
     * 试卷ID
     */
    private Long paperId;
    /**
     * 考生ID
     */
    private Long userId;
    /**
     * 考试状态（0-未开始，1-进行中，2-已完成, 3 - 弃考）
     */
    private Integer recordStatus;
    /**
     * 得分
     */
    private BigDecimal score;
    /**
     * 提交时间
     */
    private Date submitTime;
    /**
     * 批改人ID
     */
    private Long reviewerUserId;
    /**
     * 批改时间
     */
    private Date reviewerTime;
    /**
     * 创建人ID
     */
    private Long createUserId;
    /**
     * 更新人ID
     */
    private Long updateUserId;
    /**
     * 删除标识（0：未删除，2：已删除）
     */
    @TableLogic
    private String delFlag;

}
