package com.ruoyi.workingHour.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.web.domain.WhBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 项目成员业务对象
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectMembersBo extends WhBaseEntity {

    /**
     * ID
     */
    @NotNull(message = "项目成员ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 用户ID集合
     */
    private Collection<Long> userIds;

    /**
     * 项目ID
     */
    @NotNull(message = "项目ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long projectId;

    /**
     * 项目成员ID结合
     */
    @NotEmpty(message = "分配用户不能为空", groups = {AddGroup.class})
    private Collection<Long> memberIds = new ArrayList<>();

    /**
     * 创建人
     */
    private String createUserName;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 手机号
     */
    private String phonenumber;
}
