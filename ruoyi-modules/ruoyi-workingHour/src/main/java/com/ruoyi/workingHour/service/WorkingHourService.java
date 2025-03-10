package com.ruoyi.workingHour.service;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.workingHour.domain.TimeRange;
import com.ruoyi.workingHour.domain.bo.ProjectBo;
import com.ruoyi.workingHour.domain.vo.*;
import com.ruoyi.workingHour.domain.bo.WorkingHourBo;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 工时信息Service接口
 *
 */
public interface WorkingHourService {

    /**
     * 查询工时信息
     */
    WorkingHourVo queryById(Long id);

    /**
     * 查询工时信息列表
     */
    TableDataInfo<UserHourDetailVo> queryPageList(WorkingHourBo bo, PageQuery pageQuery);

    /**
     * 查询工时信息列表
     */
    List<WorkingHourVo> queryList(WorkingHourBo bo);

    /**
     * 修改工时信息
     */
    Boolean insertByBo(WorkingHourBo bo);

    /**
     * 修改工时信息
     */
    Boolean updateByBo(WorkingHourBo bo);

    /**
     * 统计项目工时信息
     * @return 统计信息
     */
    TableDataInfo<ProjectCountVo> getProjectCount(ProjectBo projectBo, PageQuery pageQuery);

    /**
     * 查询项目工时填报详情
     * @param projectId 项目ID
     * @return r
     */
    TableDataInfo<ProjectFillDetailVo> getProjectFillDetail(Long projectId, PageQuery pageQuery);

    /**
     * 员工工时统计
     * @return r
     */
    TableDataInfo<UserHourCountVo> getUserHourCount(WorkingHourBo workingHourBo, PageQuery pageQuery);

    /**
     * 校验并批量删除工时信息信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
