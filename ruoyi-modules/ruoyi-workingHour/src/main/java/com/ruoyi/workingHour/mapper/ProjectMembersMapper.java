package com.ruoyi.workingHour.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.mybatis.core.mapper.BaseMapperPlus;
import com.ruoyi.workingHour.domain.ProjectMembers;
import com.ruoyi.workingHour.domain.bo.ProjectMembersBo;
import com.ruoyi.workingHour.domain.vo.ProjectMembersVo;
import com.ruoyi.workingHour.domain.vo.ProjectVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目成员Mapper接口
 *
 * @author z
 */
public interface ProjectMembersMapper extends BaseMapperPlus<ProjectMembersMapper, ProjectMembers, ProjectMembersVo> {


    /**
     * 查询分配的项目
     * @param userId id
     * @return r
     */
    List<ProjectVo> selectAssignProjectByUserId(@Param("userId") Long userId);

    Page<ProjectMembersVo> selectMemberVoPage(@Param("page") Page page, @Param("bo") ProjectMembersBo bo);

}
