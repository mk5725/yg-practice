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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.exam.domain.vo.ExamCountVo;
import com.ruoyi.exam.domain.bo.ExamCountBo;
import com.ruoyi.exam.service.IExamCountService;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

import java.util.List;
import java.util.Arrays;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.servlet.http.HttpServletResponse;

/**
 * 成绩统计控制器
 * 前端访问路由地址为:/exam/count
 *
 * @author zkm
 * @date  2025-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/count")
public class ExamCountController extends BaseController {

    private final IExamCountService iExamCountService;

    /**
     * 查询成绩统计列表
     */
    @SaCheckPermission("exam:count:list")
    @GetMapping("/list")
    public TableDataInfo<ExamCountVo> list(ExamCountBo bo, PageQuery pageQuery) {
        return iExamCountService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出成绩统计列表
     */
    @SaCheckPermission("exam:count:export")
    @Log(title = "成绩统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ExamCountBo bo, HttpServletResponse response) {
        List<ExamCountVo> list = iExamCountService.queryList(bo);
        ExcelUtil.exportExcel(list, "成绩统计", ExamCountVo.class, response);
    }

    /**
     * 获取成绩统计详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("exam:count:query")
    @GetMapping("/{id}")
    public R<ExamCountVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(iExamCountService.queryById(id));
    }

    /**
     * 新增成绩统计
     */
    @SaCheckPermission("exam:count:add")
    @Log(title = "成绩统计", businessType = BusinessType.INSERT)
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ExamCountBo bo) {
        return toAjax(iExamCountService.insertByBo(bo));
    }

    /**
     * 修改成绩统计
     */
    @SaCheckPermission("exam:count:edit")
    @Log(title = "成绩统计", businessType = BusinessType.UPDATE)
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ExamCountBo bo) {
        return toAjax(iExamCountService.updateByBo(bo));
    }

    /**
     * 删除成绩统计
     *
     * @param ids 主键串
     */
    @SaCheckPermission("exam:count:remove")
    @Log(title = "成绩统计", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return toAjax(iExamCountService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
