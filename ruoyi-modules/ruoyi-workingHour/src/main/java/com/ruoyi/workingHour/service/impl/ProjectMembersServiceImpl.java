package com.ruoyi.workingHour.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.system.api.RemoteUserService;
import com.ruoyi.system.api.domain.SysUser;
import com.ruoyi.system.api.domain.vo.SysUserVo;
import com.ruoyi.workingHour.contants.ProjectConstant;
import com.ruoyi.workingHour.domain.ProjectMembers;
import com.ruoyi.workingHour.domain.bo.ProjectMembersBo;
import com.ruoyi.workingHour.domain.vo.ProjectMembersVo;
import com.ruoyi.workingHour.domain.vo.ProjectVo;
import com.ruoyi.workingHour.exception.BusinessException;
import com.ruoyi.workingHour.exception.ErrorCode;
import com.ruoyi.workingHour.mapper.ProjectMembersMapper;
import com.ruoyi.workingHour.service.ProjectMembersService;
import com.ruoyi.workingHour.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 项目成员Service业务层处理
 *
 */
@RequiredArgsConstructor
@Service
public class ProjectMembersServiceImpl implements ProjectMembersService {

    private final ProjectService projectService;

    private final ProjectMembersMapper projectMembersMapper;
    @DubboReference
    private RemoteUserService remoteUserService;

    /**
     * 查询项目成员
     */
    @Override
    public ProjectMembersVo queryById(Long id) {
        return projectMembersMapper.selectVoById(id);
    }

    /**
     * 查询项目成员列表
     */
    @Override
    public TableDataInfo<ProjectMembersVo> queryPageList(ProjectMembersBo bo, PageQuery pageQuery) {
        String userName = bo.getUserName();
        String phonenumber = bo.getPhonenumber();

        // 用户信息映射
        Map<Long, SysUserVo> userMap = new HashMap<>();
        List<SysUserVo> userVoList = new ArrayList<>();

        // 根据用户名称、手机号查询用户信息
        if (StrUtil.isNotBlank(userName) || StrUtil.isNotBlank(phonenumber)){
            SysUserVo sysUser = new SysUserVo();
            sysUser.setUserName(userName);
            sysUser.setPhonenumber(phonenumber);

            // 查询用户信息
            userVoList = remoteUserService.getUserVoList(sysUser);

            // 没有用户信息
            if (ObjUtil.isEmpty(userVoList)){
                return TableDataInfo.build();
            }

            userMap = userVoList.stream().collect(Collectors.toMap(SysUserVo::getUserId, Function.identity()));

            // 获取用户ID集合
            Set<Long> userIdSet = userMap.keySet();
            bo.setUserIds(userIdSet);
        }
        // 获取分页数据
        Page<ProjectMembersVo> page = projectMembersMapper.selectMemberVoPage(pageQuery.build(), bo);
        List<ProjectMembersVo> records = page.getRecords();

        if (records.isEmpty()) {
            return TableDataInfo.build(page);
        }

        // 需要补充用户信息的情况
        if (ObjUtil.isEmpty(userVoList)){
            // 获取所有用户的 ID
            Set<Long> ids = records.stream().map(ProjectMembersVo::getUserId).collect(Collectors.toSet());

            // 远程获取用户信息
            userVoList = remoteUserService.selectUserVoByIds(ids);
            userMap = userVoList.stream().collect(Collectors.toMap(SysUserVo::getUserId, Function.identity()));
        }

        // 映射用户信息
        for (ProjectMembersVo record : records) {
            SysUserVo user = userMap.get(record.getUserId());
            record.setUserName(user != null ? user.getUserName() : ProjectConstant.DEFAULT_NAME);
            record.setPhonenumber(user != null ? user.getPhonenumber() : ProjectConstant.DEFAULT_PHONE);
        }
        return TableDataInfo.build(page);
    }

    /**
     * 查询项目成员列表
     */
    @Override
    public List<ProjectMembersVo> queryList(ProjectMembersBo bo) {
        LambdaQueryWrapper<ProjectMembers> lqw = buildQueryWrapper(bo);
        return projectMembersMapper.selectVoList(lqw);
    }

    /**
     * 构造查询wrapper
     * @param bo
     * @return
     */
    private LambdaQueryWrapper<ProjectMembers> buildQueryWrapper(ProjectMembersBo bo) {
        LambdaQueryWrapper<ProjectMembers> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getProjectId() != null, ProjectMembers::getProjectId, bo.getProjectId());
        lqw.like(StringUtils.isNotBlank(bo.getCreateUserName()),
            ProjectMembers::getCreateUserName,
            bo.getCreateUserName() != null ? bo.getCreateUserName().trim() : null);
        lqw.orderBy(true, false, ProjectMembers::getCreateTime);
        return lqw;
    }

    /**
     * 分配项目成员
     */
    @Override
    public Boolean insertByBo(ProjectMembersBo bo) {
        Long projectId = bo.getProjectId();
        Collection<Long> memberIds = bo.getMemberIds();
        if (projectService.isProjectNotExist(projectId)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "项目不存在");
        }

        // 过滤不存在的用户
        Set<Long> userIds = this.filterExistingMembers(memberIds);
        if (userIds.isEmpty()){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "指定用户不存在");
        }

        // 过滤已存在的项目成员
        Set<Long> ids = this.filterAssignMembers(userIds, projectId);
        if (ids.isEmpty()){
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户已被分配");
        }

        List<ProjectMembers> membersList = ids.stream().map(id ->
            // 构建ProjectMembers对象
            ProjectMembers.builder().projectId(projectId).userId(id).build()
        ).collect(Collectors.toList());
        // 批量插入
        return projectMembersMapper.insertBatch(membersList);
    }

    /**
     * 批量删除成员记录 BY 主键
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        return projectMembersMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 根据项目Id 删除项目成员
     * @param ids 项目集合
     * @return r
     */
    @Override
    public Boolean deleteByProjectIds(Collection<Long> ids) {
        LambdaQueryWrapper<ProjectMembers> wrapper = Wrappers.<ProjectMembers>lambdaQuery()
            .in(ProjectMembers::getProjectId);
        return projectMembersMapper.delete(wrapper) > 0;
    }

    /**
     * 查询分配的项目
     * @param userId 用户ID
     * @return r
     */
    @Override
    public List<ProjectVo> queryAssignProjectByUserId(Long userId) {
        return projectMembersMapper.selectAssignProjectByUserId(userId);
    }


    /**
     * 查询可分配用户
     * @param projectId 项目ID
     * @return 用户VO
     */
    @Override
    public List<SysUserVo> queryAssignableByProjectId(Long projectId) {
        if (projectService.isProjectNotExist(projectId)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "项目不存在");
        }
        // 获取项目成员用户ID
        Set<Long> userIds = this.getProjectMembersIds(projectId);

        if (userIds.isEmpty()){
            // 返回所有用户
            return remoteUserService.selectUserVoAll();
        }

        // 过滤已分配用户
        return remoteUserService.getUserVoExcludeIds(userIds);
    }

    /**
     *  过滤存在掉不存在的用户
     * @param memberIds id
     * @return idSet
     */
    private Set<Long> filterExistingMembers(Collection<Long> memberIds) {
        if (CollectionUtils.isEmpty(memberIds)) {
            return Collections.emptySet();
        }
        return remoteUserService.selectUserVoByIds(memberIds).stream()
            .map(SysUserVo::getUserId)
            .collect(Collectors.toSet());
    }

    /**
     *  过滤掉已存在的项目成员
     *
     */
    private Set<Long> filterAssignMembers(Collection<Long> ids, Long projectId) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptySet();
        }
        HashSet<Long> idSet = new HashSet<>(ids);
        // 获取已存在的项目成员 ID
        Set<Long> existingMemberIds = projectMembersMapper.selectList(
            Wrappers.<ProjectMembers>lambdaQuery()
                .eq(ProjectMembers::getProjectId, projectId)
                .in(ProjectMembers::getUserId, idSet)
        ).stream().map(ProjectMembers::getUserId).collect(Collectors.toSet());

        // 移除已存在的成员
        idSet.removeAll(existingMemberIds);
        return idSet;
    }

    /**
     *  查询项目成员
     * @param projectId 项目ID
     * @return
     */
    private Set<Long> getProjectMembersIds(Long projectId) {

        // 获取项目成员信息
        List<ProjectMembers> projectMembers = projectMembersMapper.selectList(
            Wrappers.<ProjectMembers>lambdaQuery()
                .eq(ProjectMembers::getProjectId, projectId));

        // 返回项目成员ID
        return projectMembers.stream().map(ProjectMembers::getUserId).collect(Collectors.toSet());
    }


}
