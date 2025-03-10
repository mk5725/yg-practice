package com.ruoyi.workingHour.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.web.domain.WhBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目管理业务对象
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectBo extends WhBaseEntity {

    /**
     * ID
     */
    @NotNull(message = "ID不能为空", groups = {EditGroup.class})
    private Long id;

    /**
     * 项目ID
     */
    private Long projectId;

    /**
     * 项目名称
     */
    @NotBlank(message = "项目名称不能为空", groups = {AddGroup.class, EditGroup.class})
    private String projectName;

    /**
     * 项目状态（0: 新建, 1: 进行中, 2: 完成）
     */
    @NotBlank(message = "项目状态不能为空", groups = {AddGroup.class, EditGroup.class})
    private String status;

    /**
     * 项目简介
     */
    private String description;

    /**
     * 项目经理ID
     */
    @NotNull(message = "项目经理不能为空", groups = {AddGroup.class, EditGroup.class})
    private Long managerId;

    /**
     * 预计工时 基础限制(0.0 ~ 1000.0]
     */
    @NotNull(message = "预计工时不能为空", groups = {AddGroup.class, EditGroup.class})
    @DecimalMin(value = "0.0", inclusive = false, message = "工时必须大于 0 ", groups = { AddGroup.class, EditGroup.class })
    @DecimalMax(value = "1000.0", inclusive = true, message = "工时不能超过 1000", groups = {AddGroup.class, EditGroup.class})
    private BigDecimal expectedHour;

    @Pattern(regexp = "^[0-9]+(\\.[0-9]{1})?$", message = "工时格式只能有一位小数", groups = {AddGroup.class, EditGroup.class})
    public String getExpectedHourString() {
        return expectedHour != null ? expectedHour.toPlainString() : "";
    }


    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
}
