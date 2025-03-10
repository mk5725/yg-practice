package com.ruoyi.workingHour.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.excel.utils.ExcelUtil;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.workingHour.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.workingHour.domain.vo.ProjectVo;
import com.ruoyi.workingHour.domain.bo.ProjectBo;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

import java.util.List;
import java.util.Arrays;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.servlet.http.HttpServletResponse;

/**
 * 项目管理接口
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/project")
public class ProjectController extends BaseController {

    private final ProjectService projectService;

    /**
     * 查询项目列表信息
     */
    @SaCheckPermission("workingHour:project:list")
    @GetMapping("/list")
    public TableDataInfo<ProjectVo> list(ProjectBo bo, PageQuery pageQuery) {
        return projectService.queryPageList(bo, pageQuery);
    }


    /**
     * 导出项目列表
     */
    @SaCheckPermission("workingHour:project:export")
    @PostMapping("/export")
    public void export(ProjectBo bo, HttpServletResponse response) {
        List<ProjectVo> list = projectService.queryList(bo);
        ExcelUtil.exportExcel(list, "项目管理", ProjectVo.class, response);
    }

    /**
     * 获取项目详细信息
     * @param id 主键
     */
    @SaCheckPermission("workingHour:project:query")
    @GetMapping("/{id}")
    public R<ProjectVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(projectService.queryById(id));
    }

    /**
     * 新增项目
     */
    @SaCheckPermission("workingHour:project:add")
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ProjectBo bo) {
        return toAjax(projectService.insertByBo(bo));
    }

    /**
     * 修改项目管理
     */
    @SaCheckPermission("workingHour:project:edit")
    @Log(title = "项目管理", businessType = BusinessType.UPDATE)
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ProjectBo bo) {
        return toAjax(projectService.updateByBo(bo));
    }

    /**
     * 删除项目
     *
     * @param ids 主键串
     */
    @SaCheckPermission("workingHour:project:remove")
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "项目不能为空") @PathVariable Long[] ids) {
        return toAjax(projectService.deleteWithValidByIds(Arrays.asList(ids), true));
    }

    /**
     * 查询所有项目名称
     *
     */
    @SaCheckPermission("workingHour:project:query")
    @GetMapping("/name")
    public R<List<ProjectVo>> getProjectName() {
        return R.ok(projectService.queryProjectName());
    }

}
