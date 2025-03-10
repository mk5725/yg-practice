package com.ruoyi.workingHour.service;

import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.workingHour.domain.bo.ProjectBo;
import com.ruoyi.workingHour.domain.vo.ProjectVo;

import java.util.Collection;
import java.util.List;

/**
 * 项目管理Service接口
 */
public interface ProjectService {

    /**
     * 查询项目管理
     */
    ProjectVo queryById(Long id);


    /**
     * 查询项目管理列表
     */
    TableDataInfo<ProjectVo> queryPageList(ProjectBo bo, PageQuery pageQuery);

    /**
     * 查询项目管理列表
     */
    List<ProjectVo> queryList(ProjectBo bo);

    /**
     * 修改项目管理
     */
    Boolean insertByBo(ProjectBo bo);

    /**
     * 修改项目管理
     */
    Boolean updateByBo(ProjectBo bo);

    /**
     * 查询所有项目名
     */
    List<ProjectVo> queryProjectName();


    /**
     * 校验并批量删除项目管理信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 判断项目是否存在
     * @param projectId 项目ID
     * @return true 如果项目存在，false 如果项目不存在
     */
    boolean isProjectExist(Long projectId);

    boolean isProjectNotExist(Long projectId);
}
