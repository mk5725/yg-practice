package com.ruoyi.exam.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import java.math.BigDecimal;
import com.ruoyi.common.core.web.domain.LsBaseEntity;

/**
 * 成绩统计业务对象
 *
 * @author zkm
 * @date  2025-03
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ExamCountBo extends LsBaseEntity {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 试卷ID
     */
    private Long paperId;

    /**
     * 平均分
     */
    @NotNull(message = "平均分不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal avgScore;

    /**
     * 最高分
     */
    @NotNull(message = "最高分不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal maxScore;

    /**
     * 最低分
     */
    @NotNull(message = "最低分不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal minScore;

    /**
     * 及格率
     */
    @NotNull(message = "及格率不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal passRate;


}
