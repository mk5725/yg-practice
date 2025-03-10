package com.ruoyi.workingHour.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.system.api.RemoteUserService;
import com.ruoyi.system.api.domain.vo.SysUserVo;
import com.ruoyi.workingHour.contants.ProjectConstant;
import com.ruoyi.workingHour.domain.Project;
import com.ruoyi.workingHour.domain.ProjectMembers;
import com.ruoyi.workingHour.domain.WorkingHour;
import com.ruoyi.workingHour.domain.bo.ProjectBo;
import com.ruoyi.workingHour.domain.enums.ProjectStatusEnum;
import com.ruoyi.workingHour.domain.vo.ProjectVo;
import com.ruoyi.workingHour.exception.BusinessException;
import com.ruoyi.workingHour.exception.ErrorCode;
import com.ruoyi.workingHour.mapper.ProjectMapper;
import com.ruoyi.workingHour.mapper.ProjectMembersMapper;
import com.ruoyi.workingHour.mapper.WorkingHourMapper;
import com.ruoyi.workingHour.service.ProjectMembersService;
import com.ruoyi.workingHour.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 项目管理Service业务层处理
 *
 */
@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectMapper projectMapper;

    private final ProjectMembersMapper projectMembersMapper;

    private final WorkingHourMapper workingHourMapper;

//    private final ProjectMembersService projectMembersService;

    @DubboReference
    private RemoteUserService remoteUserService;

    /**
     * 查询项目详情
     */
    @Override
    public ProjectVo queryById(Long id) {
        return projectMapper.selectVoById(id);
    }

    /**
     * 查询项目列表分页信息
     */
    @Override
    public TableDataInfo<ProjectVo> queryPageList(ProjectBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Project> lqw = buildQueryWrapper(bo);

        // 查询分页数据
        Page<ProjectVo> voPage = projectMapper.selectVoPage(pageQuery.build(), lqw);
        List<ProjectVo> projectVoList = voPage.getRecords();

        // 无数据
        if (projectVoList.isEmpty()){
            return TableDataInfo.build(voPage);
        }

        // 获取项目经理ID集合
        Set<Long> ids = projectVoList.stream().
            map(ProjectVo::getManagerId)
            .collect(Collectors.toSet());

        if (!ids.isEmpty()) {
            // 远程获取用户信息
            List<SysUserVo> userVoList = remoteUserService.selectUserVoByIds(ids);

            // 将userVoList 转map
            Map<Long, String> userMap = userVoList.stream()
                .collect(Collectors.toMap(SysUserVo::getUserId, SysUserVo::getUserName));

            // 将项目经理名称填充到 projectVoList 中
            projectVoList.forEach(projectVo -> {
                String managerName = userMap.get(projectVo.getManagerId());
                projectVo.setManagerName(managerName != null ? managerName : ProjectConstant.DEFAULT_NAME);
            });
        }
        return TableDataInfo.build(voPage);
    }

    /**
     * 查询项目管理列表
     */
    @Override
    public List<ProjectVo> queryList(ProjectBo bo) {
        LambdaQueryWrapper<Project> lqw = buildQueryWrapper(bo);
        return projectMapper.selectVoList(lqw);
    }


    // 构建查询条件
    private LambdaQueryWrapper<Project> buildQueryWrapper(ProjectBo bo) {
        String projectName = bo.getProjectName() != null ? bo.getProjectName().trim() : null;
        String description = bo.getDescription() != null ? bo.getDescription().trim() : null;
        LambdaQueryWrapper<Project> lqw = Wrappers.lambdaQuery();
        lqw.eq((ObjUtil.isNotNull(bo.getId())), Project::getId, bo.getId());
        lqw.like(StringUtils.isNotBlank(projectName), Project::getProjectName, projectName);
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), Project::getStatus, bo.getStatus());
        lqw.like(StringUtils.isNotBlank(description), Project::getDescription, description);
        lqw.eq(bo.getManagerId() != null, Project::getManagerId, bo.getManagerId());
        lqw.orderBy(true, false, Project::getCreateTime);
        return lqw;
    }

    /**
     * 新增项目
     */
    @Override
    public Boolean insertByBo(ProjectBo bo) {
        Project project = BeanUtil.toBean(bo, Project.class);
        // 数据检验
        this.validStatusAndUserId(project);
        // 生成项目编号
        project.setProjectNo(ProjectConstant.PROJECT_NO_PREFIX + RandomUtil.randomNumbers(ProjectConstant.PROJECT_NO_LENGTH));
        try {
            int rows = projectMapper.insert(project);
            if (rows == 0) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "新增项目失败");
            }
            return true;
        } catch (DuplicateKeyException e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "项目名重复");
        }
    }

    /**
     * 修改项目
     */
    @Override
    public Boolean updateByBo(ProjectBo bo) {
        Project project = BeanUtil.toBean(bo, Project.class);

        // 数据校验
        this.validStatusAndUserId(project);

        return projectMapper.updateById(project) > 0;
    }


    /**
     * 查询所有项目名称列表
     */
    @Override
    public List<ProjectVo> queryProjectName() {
        return projectMapper.selectVoList(Wrappers.lambdaQuery(Project.class)
            .select(Project::getId, Project::getProjectName));
    }

    /**
     * 校验项目状态，项目经理
     */
    private void validStatusAndUserId(Project project) {
        // 校验项目状态
        String status = project.getStatus();
        if (ObjUtil.isNull(ProjectStatusEnum.getEnumByValue(status))){
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "项目状态不能为空");
        }
        // 校验项目经理是否存在
        if (remoteUserService.selectUserNameById(project.getManagerId()) == null){
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "项目经理不能为空");
        }
    }

    /**
     * 批量删除项目
     */
    @Override
    @Transactional
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {

        // 删除项目
        boolean flag = projectMapper.deleteBatchIds(ids) > 0;

        if (flag) {
            // 根据项目ID移除项目成员
            projectMembersMapper.delete(Wrappers
                .lambdaQuery(ProjectMembers.class)
                .in(ProjectMembers::getProjectId, ids));
            // 移除项目工时
            workingHourMapper.delete(Wrappers
                .lambdaQuery(WorkingHour.class)
                .in(WorkingHour::getProjectId, ids));
        } else {
            // 删除项目失败
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "项目删除失败");
        }
        return true;
    }


    /**
     * 根据项目ID判断项目是否存在
     *
     * @param projectId 项目ID
     * @return true 项目存在，false 项目不存在
     */
    @Override
    public boolean isProjectExist(Long projectId) {
        if (projectId == null) {
            return false;
        }
        return projectMapper.exists(Wrappers.lambdaQuery(Project.class)
            .eq(Project::getId, projectId));
    }

    @Override
    public boolean isProjectNotExist(Long projectId) {
        return !isProjectExist(projectId);
    }
}
