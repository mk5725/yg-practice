package com.ruoyi.exam.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.validate.QueryGroup;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.excel.utils.ExcelUtil;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.exam.domain.vo.ExamPaperVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.exam.domain.vo.ExamRecordVo;
import com.ruoyi.exam.domain.bo.ExamRecordBo;
import com.ruoyi.exam.service.IExamRecordService;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

import java.util.List;
import java.util.Arrays;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.servlet.http.HttpServletResponse;

/**
 * 考试记录信息控制器
 * 前端访问路由地址为:/exam/record
 *
 * @author zkm
 * @date 2025-03-15
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/record")
public class ExamRecordController extends BaseController {

    private final IExamRecordService iExamRecordService;

    /**
     * 查询考试记录信息列表 （包括批改列表）
     */
    @SaCheckPermission("exam:record:list")
    @GetMapping("/list")
    public TableDataInfo<ExamRecordVo> list(ExamRecordBo bo, PageQuery pageQuery) {
        return iExamRecordService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出考试记录信息列表
     */
    @SaCheckPermission("exam:record:export")
    @Log(title = "考试记录信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ExamRecordBo bo, HttpServletResponse response) {
        List<ExamRecordVo> list = iExamRecordService.queryList(bo);
        ExcelUtil.exportExcel(list, "考试记录信息", ExamRecordVo.class, response);
    }

    /**
     * 获取发放考试记录 - 详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("exam:record:query")
    @GetMapping("/{id}")
    public R<ExamPaperVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(iExamRecordService.queryById(id));
    }

    /**
     * 新增考试记录信息
     */
    @SaCheckPermission("exam:record:add")
    @Log(title = "考试记录信息", businessType = BusinessType.INSERT)
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ExamRecordBo bo) {
        return toAjax(iExamRecordService.insertByBo(bo));
    }

    /**
     * 修改考试记录信息
     */
    @SaCheckPermission("exam:record:edit")
    @Log(title = "考试记录信息", businessType = BusinessType.UPDATE)
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ExamRecordBo bo) {
        return toAjax(iExamRecordService.updateByBo(bo));
    }

    /**
     * 删除考试记录信息
     *
     * @param ids 主键串
     */
    @SaCheckPermission("exam:record:remove")
    @Log(title = "考试记录信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return toAjax(iExamRecordService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
