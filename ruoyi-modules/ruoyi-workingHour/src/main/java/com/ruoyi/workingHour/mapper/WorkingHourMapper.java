package com.ruoyi.workingHour.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.mybatis.core.mapper.BaseMapperPlus;
import com.ruoyi.workingHour.domain.WorkingHour;
import com.ruoyi.workingHour.domain.bo.ProjectBo;
import com.ruoyi.workingHour.domain.bo.WorkingHourBo;
import com.ruoyi.workingHour.domain.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工时信息Mapper接口
 *
 */
public interface WorkingHourMapper extends BaseMapperPlus<WorkingHourMapper, WorkingHour, WorkingHourVo> {

    /**
     * 查询工时信息
     *
     */
    Page<UserHourDetailVo> selectWorkHourPage(@Param("page") Page page, @Param("bo") WorkingHourBo bo);

    /**
     * 查询项目工时填报详情
     * @return r
     */
    Page<ProjectFillDetailVo> getProjectFillDetail(@Param("page") Page page, @Param("projectId") Long projectId);

    /**
     * 统计员工工时信息
     * @return r
     */
    Page<UserHourCountVo> getUserHourCount(@Param("page") Page page, @Param("bo") WorkingHourBo bo);

    Page<UserHourDetailVo> getHourDetail(@Param("page") Page<Object> page, @Param("userId") Long userId);

    Page<ProjectCountVo> getProjectCountAll(@Param("page")Page<Object> build, @Param("bo") ProjectBo  bo);
}
