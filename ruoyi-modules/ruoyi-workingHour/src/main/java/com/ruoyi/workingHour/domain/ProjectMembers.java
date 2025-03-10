package com.ruoyi.workingHour.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.web.domain.WhBaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 项目成员对象 project_members
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("project_members")
@Builder
public class ProjectMembers extends WhBaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 项目成员ID
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
     * 删除标识（0：未删除，2：删除）
     */
    @TableLogic
    private String delFlag;

}
