package com.ruoyi.exam.domain.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.exam.domain.vo.ExamPaperTitleVo;
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
 * 考试记录信息业务对象
 *
 * @author zkm
 * @date 2025-03-15
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ExamRecordBo extends LsBaseEntity {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 主键ID
     */
    private Long recordId;

    /**
     * 发放记录ID
     */
    @NotNull(message = "发放记录ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long sendId;

    /**
     * 考生ID
     */
    @NotNull(message = "考生ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 试卷名称
     *
     */
    private String paperName;


    /**
     * 试卷名称
     *
     */
    private Long paperId;

    /**
     * 考试状态（0-未开始，1-进行中，2-已完成, 3 - 弃考）
     */
    @NotNull(message = "考试状态（0-未开始，1-进行中，2-已完成, 3 - 弃考）不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer recordStatus;

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

    /**
     * 试卷标题 - 题目列表数据
     */
    private List<ExamPaperTitleVo> paperTitleList = new ArrayList<>();

    /**
     * 批改 - 答题记录
     */
    private List<AnswerDetailBo> answerDetailList = new ArrayList<>();


}
