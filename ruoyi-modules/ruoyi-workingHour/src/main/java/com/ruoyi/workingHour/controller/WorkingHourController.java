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
import com.ruoyi.common.satoken.utils.LoginHelper;
import com.ruoyi.workingHour.domain.TimeRange;
import com.ruoyi.workingHour.domain.bo.ProjectBo;
import com.ruoyi.workingHour.domain.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.workingHour.domain.bo.WorkingHourBo;
import com.ruoyi.workingHour.service.WorkingHourService;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

import java.util.List;
import java.util.Arrays;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.servlet.http.HttpServletResponse;

/**
 * 工时接口
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/hour")
public class WorkingHourController extends BaseController {

    private final WorkingHourService workingHourService;

    /**
     * 查询我的工时信息
     */
    @SaCheckPermission("workingHour:hour:list")
    @GetMapping("/list")
    public TableDataInfo<UserHourDetailVo> list(WorkingHourBo bo, PageQuery pageQuery) {
        bo.setUserId(LoginHelper.getUserId());
        return workingHourService.queryPageList(bo, pageQuery);
    }

    /**
     * 查询指定用户工时信息
     */
    @SaCheckPermission("workingHour:hour:list")
    @GetMapping("/list/user")
    public TableDataInfo<UserHourDetailVo> listByUserId(WorkingHourBo bo, PageQuery pageQuery) {
        return workingHourService.queryPageList(bo, pageQuery);
    }

    /**
     * 统计项目工时信息
     * @return r
     */
    @SaCheckPermission("workingHour:hour:query")
    @GetMapping("/count/project")
    public TableDataInfo<ProjectCountVo> getProjectCount(ProjectBo bo, PageQuery pageQuery) {

        return workingHourService.getProjectCount(bo, pageQuery);
    }

    /**
     * 查询项目工时填报详情
     * @param projectId 项目ID
     * @return r
     */
    @SaCheckPermission("workingHour:hour:query")
    @GetMapping("/project/detail")
    public TableDataInfo<ProjectFillDetailVo> getProjectFillDetail(
        @NotNull(message = "项目不能为空") Long projectId, PageQuery pageQuery) {
        return workingHourService.getProjectFillDetail(projectId, pageQuery);
    }

    /**
     * 统计员工工时
     *
     * @return r
     */
    @SaCheckPermission("workingHour:hour:query")
    @GetMapping("/count/member")
    public TableDataInfo<UserHourCountVo> getUserHourCount(WorkingHourBo workingHourBo, PageQuery pageQuery) {

        return workingHourService.getUserHourCount(workingHourBo, pageQuery);
    }

    /**
     * 导出工时信息列表
     */
    @SaCheckPermission("workingHour:hour:export")
    @PostMapping("/export")
    public void export(WorkingHourBo bo, HttpServletResponse response) {
        List<WorkingHourVo> list = workingHourService.queryList(bo);
        ExcelUtil.exportExcel(list, "工时信息", WorkingHourVo.class, response);
    }

    /**
     * 获取工时信息详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("workingHour:hour:query")
    @GetMapping("/{id}")
    public R<WorkingHourVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(workingHourService.queryById(id));
    }
    /**
     * 新增工时信息
     */
    @SaCheckPermission("workingHour:hour:add")
    @Log(title = "工时信息", businessType = BusinessType.INSERT)
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody WorkingHourBo bo) {
        bo.setUserId(LoginHelper.getUserId());
        return toAjax(workingHourService.insertByBo(bo));
    }

    /**
     * 修改工时信息
     */
    @SaCheckPermission("workingHour:hour:edit")
    @Log(title = "工时信息", businessType = BusinessType.UPDATE)
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody WorkingHourBo bo) {
        return toAjax(workingHourService.updateByBo(bo));
    }


    /**
     * 删除工时信息
     *
     * @param ids 主键串
     */
    @SaCheckPermission("workingHour:hour:remove")
    @Log(title = "工时信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return toAjax(workingHourService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
