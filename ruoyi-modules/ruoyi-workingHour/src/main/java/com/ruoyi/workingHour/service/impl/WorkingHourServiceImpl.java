package com.ruoyi.workingHour.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.system.api.RemoteUserService;
import com.ruoyi.system.api.domain.vo.SysUserVo;
import com.ruoyi.workingHour.contants.ProjectConstant;
import com.ruoyi.workingHour.domain.ProjectMembers;
import com.ruoyi.workingHour.domain.WorkingHour;
import com.ruoyi.workingHour.domain.bo.ProjectBo;
import com.ruoyi.workingHour.domain.bo.WorkingHourBo;
import com.ruoyi.workingHour.domain.vo.*;
import com.ruoyi.workingHour.exception.BusinessException;
import com.ruoyi.workingHour.exception.ErrorCode;
import com.ruoyi.workingHour.mapper.ProjectMapper;
import com.ruoyi.workingHour.mapper.ProjectMembersMapper;
import com.ruoyi.workingHour.mapper.WorkingHourMapper;
import com.ruoyi.workingHour.service.ProjectService;
import com.ruoyi.workingHour.service.WorkingHourService;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 工时信息Service业务层处理
 *
 * @author ruoyi
 * @date 2025-02-23
 */
@RequiredArgsConstructor
@Service
public class WorkingHourServiceImpl implements WorkingHourService {

    private final WorkingHourMapper workingHourMapper;

    private final ProjectMapper projectMapper;

    private final ProjectService projectService;

    private final ProjectMembersMapper projectMembersMapper;

    @DubboReference
    private RemoteUserService remoteUserService;

    /**
     * 查询工时信息
     */
    @Override
    public WorkingHourVo queryById(Long id) {
        return workingHourMapper.selectVoById(id);
    }

    /**
     * 查询工时信息列表
     */
    @Override
    public TableDataInfo<UserHourDetailVo> queryPageList(WorkingHourBo bo, PageQuery pageQuery) {
        Page<UserHourDetailVo> page = workingHourMapper.selectWorkHourPage(pageQuery.build(), bo);

        // 获取查询结果
        List<UserHourDetailVo> records = page.getRecords();

        // 填充星期几
        records.forEach(h -> {
            Date date = h.getUpdateTime();
            if (date != null) {
                LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());

                // 获取星期
                String weekDayName = localDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.CHINA);
                h.setWeek(weekDayName);
            }
        });
        return TableDataInfo.build(page);
    }

    /**
     * 查询工时信息列表
     */
    @Override
    public List<WorkingHourVo> queryList(WorkingHourBo bo) {
        LambdaQueryWrapper<WorkingHour> lqw = buildQueryWrapper(bo);
        return workingHourMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<WorkingHour> buildQueryWrapper(WorkingHourBo bo) {
        LambdaQueryWrapper<WorkingHour> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, WorkingHour::getUserId, bo.getUserId());
        lqw.eq(bo.getProjectId() != null, WorkingHour::getProjectId, bo.getProjectId());
        lqw.like(StringUtils.isNotBlank(bo.getDescription()), WorkingHour::getDescription, bo.getDescription());
        lqw.gt(bo.getStartTime() != null, WorkingHour::getUpdateTime, bo.getStartTime());
        lqw.le(bo.getEndTime() != null, WorkingHour::getUpdateTime, bo.getEndTime());
        // 按填报日期倒序排列
        lqw.orderBy(true,false, WorkingHour::getUpdateTime);
        return lqw;
    }

    /**
     * 新增工时信息
     */
    @Override
    public Boolean insertByBo(WorkingHourBo bo) {
        WorkingHour add = BeanUtil.toBean(bo, WorkingHour.class);

        // 校验 项目权限，工时上限
        this.validEntityBeforeSave(add);

        return workingHourMapper.insert(add) > 0;
    }

    /**
     * 修改工时信息
     */
    @Override
    public Boolean updateByBo(WorkingHourBo bo) {
        WorkingHour update = BeanUtil.toBean(bo, WorkingHour.class);

        // 校验 项目权限，工时上限
        this.validEntityBeforeSave(update);

        return workingHourMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(WorkingHour workingHour) {
        Long userId = workingHour.getUserId();
        Long projectId = workingHour.getProjectId();
        Long id = workingHour.getId();
        // 如果是修改记录
        WorkingHourVo currentVo = id != null ? workingHourMapper.selectVoById(id) : null;
        if (id != null && currentVo == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "记录不存在");
        }

        // 项目判空
        if (projectService.isProjectNotExist(projectId)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "项目不存在");
        }

        // 项目权限
        boolean exists = projectMembersMapper.exists(Wrappers
            .lambdaQuery(ProjectMembers.class)
            .eq(ProjectMembers::getProjectId, projectId)
            .eq(ProjectMembers::getUserId, userId));
        if (!exists){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "暂无项目权限，请联系项目负责人");
        }

        // 将填报时间转换为填报当天的开始时间和结束时间
        LocalDate fillDate = workingHour.getUpdateTime().toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
        Date startOfDay = Date.from(fillDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endOfDay = Date.from(fillDate.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());

        // 查询当天工时记录
        List<WorkingHourVo> workingHourVoList = workingHourMapper.selectVoList(Wrappers
            .lambdaQuery(WorkingHour.class)
            .select(WorkingHour::getWorkHours)
            .eq(WorkingHour::getUserId, userId)
            // 大于等于当天00:00:00
            .ge(WorkingHour::getUpdateTime, startOfDay)
            // 小于等于当天23:59:59
            .le(WorkingHour::getUpdateTime, endOfDay));

        // 计算当天工时总和
        BigDecimal countHour = workingHourVoList.stream()
            .map(WorkingHourVo::getWorkHours)
            .reduce(workingHour.getWorkHours(), BigDecimal::add);

        // 单日工时上限标识
        boolean flag = ProjectConstant.MAX_HOUR_DAY.compareTo(countHour) < 0;

        // 如果是修改工时，且已达到上限，则调整计算
        if (currentVo != null && flag){
            BigDecimal updatedHours  = countHour.subtract(currentVo.getWorkHours());
            flag = ProjectConstant.MAX_HOUR_DAY.compareTo(updatedHours ) < 0;
        }
        if (flag){
            String msg = String.format("单日工时上限不能超过%s小时", ProjectConstant.MAX_HOUR_DAY);
            throw new BusinessException(ErrorCode.OPERATION_ERROR, msg);
        }
    }

    /**
     * 统计项目工时信息
     * @return r
     */
    @Override
    public TableDataInfo<ProjectCountVo> getProjectCount(ProjectBo projectBo, PageQuery pageQuery) {

        Page<ProjectCountVo> page = workingHourMapper.getProjectCountAll(pageQuery.build(), projectBo);

        return TableDataInfo.build(page);
    }

    /**
     * 查询项目工时填报详情
     *
     * @param projectId 项目ID
     * @return r
     */
    @Override
    public TableDataInfo<ProjectFillDetailVo> getProjectFillDetail(Long projectId, PageQuery pageQuery) {
        // 查询项目填报详情
        Page<ProjectFillDetailVo> page = workingHourMapper.getProjectFillDetail(pageQuery.build(), projectId);
        List<ProjectFillDetailVo> records = page.getRecords();

        // 获取用户Id集合
        Set<Long> userIds = records.stream().map(ProjectFillDetailVo::getFillUserId).collect(Collectors.toSet());

        // 远程获取用户信息
        Map<Long, String> userMap = remoteUserService.selectUserVoByIds(userIds).stream().collect(Collectors.toMap(SysUserVo::getUserId, SysUserVo::getUserName));

        // 填充用户名
        records.forEach(p -> {
            String name = userMap.get(p.getFillUserId());
            p.setFillUserName(StrUtil.isNotBlank(name) ? name : ProjectConstant.DEFAULT_NAME);
        });

        return TableDataInfo.build(page);
    }

    /**
     * 员工工时统计
     *
     * @return r
     */
    @Override
    public TableDataInfo<UserHourCountVo> getUserHourCount(WorkingHourBo workingHourBo, PageQuery pageQuery) {
        // 查询每个员工的工时信息
        Page<UserHourCountVo> page = workingHourMapper.getUserHourCount(pageQuery.build(), workingHourBo);
        List<UserHourCountVo> records = page.getRecords();

        // 没有员工填报工时信息
        if (records.isEmpty()) {
            page.setTotal(0);
            page.setRecords(Collections.emptyList());
            return TableDataInfo.build(page);
        }

        // 获取所有填报用户Id集合
        Set<Long> userIds = records.stream().map(UserHourCountVo::getUserId).collect(Collectors.toSet());
        // 远程获取用户信息
        List<SysUserVo> userVoList = remoteUserService.selectUserVoByIds(userIds);

        Map<Long, String> userVoMap = userVoList.stream().collect(Collectors.toMap(SysUserVo::getUserId, SysUserVo::getUserName));

        WorkingHourBo wh = new WorkingHourBo();
        BeanUtil.copyProperties(workingHourBo, wh);
        // 填充用户工时信息
        records.forEach(h -> {
            wh.setUserId(h.getUserId());
            // 填充用户名
            String userName = userVoMap.get(h.getUserId());
            h.setUserName(StrUtil.isNotBlank(userName) ? userName : ProjectConstant.DEFAULT_NAME);

            // 填充项目列表
            List<ProjectVo> projectList = projectMapper.getFillProject(wh);
            Set<String> projectSet = projectList.stream().map(ProjectVo::getProjectName).collect(Collectors.toSet());
            h.setProjectList(projectSet);
        });

        return TableDataInfo.build(page);
    }

    /**
     * 批量删除工时信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        return workingHourMapper.deleteBatchIds(ids) > 0;
    }
}
