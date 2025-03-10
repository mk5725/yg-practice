package com.ruoyi.workingHour.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.excel.utils.ExcelUtil;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.common.satoken.utils.LoginHelper;
import com.ruoyi.system.api.domain.vo.SysUserVo;
import com.ruoyi.workingHour.domain.bo.ProjectMembersBo;
import com.ruoyi.workingHour.domain.vo.ProjectMembersVo;
import com.ruoyi.workingHour.domain.vo.ProjectVo;
import com.ruoyi.workingHour.service.ProjectMembersService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * 项目成员接口
 * @author z
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class ProjectMembersController extends BaseController {

    private final ProjectMembersService projectMembersService;

    /**
     * 查询项目成员列表
     */
    @SaCheckPermission("workingHour:members:list")
    @GetMapping("/list")
    public TableDataInfo<ProjectMembersVo> list(ProjectMembersBo bo, PageQuery pageQuery) {
        return projectMembersService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出项目成员列表
     */
    @SaCheckPermission("workingHour:members:export")
    @PostMapping("/export")
    public void export(ProjectMembersBo bo, HttpServletResponse response) {
        List<ProjectMembersVo> list = projectMembersService.queryList(bo);
        ExcelUtil.exportExcel(list, "项目成员", ProjectMembersVo.class, response);
    }

    /**
     * 获取项目成员详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("workingHour:members:query")
    @GetMapping("/{id}")
    public R<ProjectMembersVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(projectMembersService.queryById(id));
    }

    /**
     * 获取当前登录用户已分配的项目
     * @return r
     */
    @SaCheckPermission("workingHour:members:query")
    @GetMapping("/project/current")
    public R<List<ProjectVo>> getAssignProjectByUserId() {

        return R.ok(projectMembersService.queryAssignProjectByUserId(LoginHelper.getUserId()));
    }

    /**
     * 获取可分配用户
     * @param projectId 项目ID
     * @return UserVo
     */
    @SaCheckPermission("workingHour:members:query")
    @GetMapping("/user/{projectId}")
    public R<List<SysUserVo>> getAssignableUserVo(@PathVariable @NotNull(message = "项目不能为空") Long projectId) {
        return R.ok(projectMembersService.queryAssignableByProjectId(projectId));
    }

    /**
     * 分配项目成员
     */
    @SaCheckPermission("workingHour:members:add")
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ProjectMembersBo bo) {
        return toAjax(projectMembersService.insertByBo(bo));
    }

    /**
     * 删除项目成员
     * @param ids 主键串
     */
    @SaCheckPermission("workingHour:members:remove")
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return toAjax(projectMembersService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
