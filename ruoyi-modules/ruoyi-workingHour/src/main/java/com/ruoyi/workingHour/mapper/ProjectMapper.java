package com.ruoyi.workingHour.mapper;


import com.ruoyi.common.mybatis.core.mapper.BaseMapperPlus;
import com.ruoyi.workingHour.domain.Project;
import com.ruoyi.workingHour.domain.bo.WorkingHourBo;
import com.ruoyi.workingHour.domain.vo.ProjectVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 项目管理Mapper接口
 *
 * @author z
 */
public interface ProjectMapper extends BaseMapperPlus<ProjectMapper, Project, ProjectVo> {

    /**
     * 查询用户填报项目
     */
    List<ProjectVo> getFillProject(@Param("bo") WorkingHourBo bo);


    /**
     * 根据用户ID批量查询所有项目
     * @param userIds ids
     * @return
     */
    Map<Long, Set<String>> getProjectsByUserIds(@Param("userIds") Set<Long> userIds);

    int insertProject(Project project);

}
