package com.ruoyi.workingHour.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 工时信息业务对象
 *
 */

@Data
public class WorkingHourBo implements Serializable {

    /**
     * 工时ID
     */
    @NotNull(message = "公式ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 项目ID
     */
    @NotNull(message = "项目ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long projectId;

    /**
     * 工时 基础限制(0.0 ~ 1000.0]
     */
    @NotNull(message = "工时不能为空", groups = {AddGroup.class, EditGroup.class})
    @DecimalMin(value = "0.0", inclusive = false, message = "工时必须大于 0 ", groups = { AddGroup.class, EditGroup.class })
    @DecimalMax(value = "1000.0", inclusive = true, message = "工时不能超过 1000", groups = {AddGroup.class, EditGroup.class})
    private BigDecimal workHours;

    @Pattern(regexp = "^[0-9]+(\\.[0-9]{1})?$", message = "工时格式只能有一位小数", groups = {AddGroup.class, EditGroup.class})
    public String getWorkHoursString() {
        return workHours != null ? workHours.toPlainString() : "";
    }

    /**
     * 描述
     */
    @NotBlank(message = "描述不能为空", groups = { AddGroup.class, EditGroup.class })
    private String description;

    /**
     * 填报时间
     */
    @NotNull(message = "日期不能为空", groups = { AddGroup.class, EditGroup.class })
    @PastOrPresent(message = "填报日期不能为未来时间")
    private Date updateTime;

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
