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
import com.ruoyi.exam.domain.vo.ExamPaperTitleVo;
import com.ruoyi.exam.domain.bo.ExamPaperTitleBo;
import com.ruoyi.exam.service.IExamPaperTitleService;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

import java.util.List;
import java.util.Arrays;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.servlet.http.HttpServletResponse;

/**
 * 试卷标题控制器
 * 前端访问路由地址为:/exam/paperTitle
 *
 * @author zkm
 * @date 2025-03-11
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/paperTitle")
public class ExamPaperTitleController extends BaseController {

    private final IExamPaperTitleService iExamPaperTitleService;

    /**
     * 查询试卷标题列表
     */
    @SaCheckPermission("exam:paperTitle:list")
    @GetMapping("/list")
    public TableDataInfo<ExamPaperTitleVo> list(ExamPaperTitleBo bo, PageQuery pageQuery) {
        return iExamPaperTitleService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出试卷标题列表
     */
    @SaCheckPermission("exam:paperTitle:export")
    @Log(title = "试卷标题", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ExamPaperTitleBo bo, HttpServletResponse response) {
        List<ExamPaperTitleVo> list = iExamPaperTitleService.queryList(bo);
        ExcelUtil.exportExcel(list, "试卷标题", ExamPaperTitleVo.class, response);
    }

    /**
     * 获取试卷标题详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("exam:paperTitle:query")
    @GetMapping("/{id}")
    public R<ExamPaperTitleVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(iExamPaperTitleService.queryById(id));
    }

    /**
     * 新增试卷标题
     */
    @SaCheckPermission("exam:paperTitle:add")
    @Log(title = "试卷标题", businessType = BusinessType.INSERT)
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ExamPaperTitleBo bo) {
        return toAjax(iExamPaperTitleService.insertByBo(bo));
    }

    /**
     * 修改试卷标题
     */
    @SaCheckPermission("exam:paperTitle:edit")
    @Log(title = "试卷标题", businessType = BusinessType.UPDATE)
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ExamPaperTitleBo bo) {
        return toAjax(iExamPaperTitleService.updateByBo(bo));
    }

    /**
     * 删除试卷标题
     *
     * @param ids 主键串
     */
    @SaCheckPermission("exam:paperTitle:remove")
    @Log(title = "试卷标题", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return toAjax(iExamPaperTitleService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
