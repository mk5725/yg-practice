package com.ruoyi.workingHour.service;

import com.ruoyi.system.api.domain.vo.SysUserVo;
import com.ruoyi.workingHour.domain.vo.ProjectMembersVo;
import com.ruoyi.workingHour.domain.bo.ProjectMembersBo;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.workingHour.domain.vo.ProjectVo;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 项目成员Service接口
 */
public interface ProjectMembersService {

    /**
     * 查询项目成员
     */
    ProjectMembersVo queryById(Long id);

    /**
     * 查询项目成员列表
     */
    TableDataInfo<ProjectMembersVo> queryPageList(ProjectMembersBo bo, PageQuery pageQuery);

    /**
     * 查询项目成员列表
     */
    List<ProjectMembersVo> queryList(ProjectMembersBo bo);

    /**
     * 分配项目成员
     */
    Boolean insertByBo(ProjectMembersBo bo);

    /**
     * 校验并批量删除项目成员信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);


    /**
     * 批量删除项目成员
     * @param ids 项目集合
     * @return r
     */
    Boolean deleteByProjectIds(Collection<Long> ids);

    /**
     * 查询分配的项目
     * @param userId 用户ID
     * @return r
     */
    List<ProjectVo> queryAssignProjectByUserId(Long userId);

    /**
     * 根据项目ID 查询可分配的用户信息
     */
    List<SysUserVo> queryAssignableByProjectId(Long projectId);

}
