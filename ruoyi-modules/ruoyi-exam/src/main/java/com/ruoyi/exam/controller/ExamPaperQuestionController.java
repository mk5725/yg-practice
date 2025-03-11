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
import com.ruoyi.exam.domain.vo.ExamPaperQuestionVo;
import com.ruoyi.exam.domain.bo.ExamPaperQuestionBo;
import com.ruoyi.exam.service.IExamPaperQuestionService;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

import java.util.List;
import java.util.Arrays;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.servlet.http.HttpServletResponse;

/**
 * 试卷题目关联控制器
 * 前端访问路由地址为:/exam/paperQuestion
 *
 * @author zkm
 * @date  2025-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/paperQuestion")
public class ExamPaperQuestionController extends BaseController {

    private final IExamPaperQuestionService iExamPaperQuestionService;

    /**
     * 查询试卷题目关联列表
     */
    @SaCheckPermission("exam:paperQuestion:list")
    @GetMapping("/list")
    public TableDataInfo<ExamPaperQuestionVo> list(ExamPaperQuestionBo bo, PageQuery pageQuery) {
        return iExamPaperQuestionService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出试卷题目关联列表
     */
    @SaCheckPermission("exam:paperQuestion:export")
    @Log(title = "试卷题目关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ExamPaperQuestionBo bo, HttpServletResponse response) {
        List<ExamPaperQuestionVo> list = iExamPaperQuestionService.queryList(bo);
        ExcelUtil.exportExcel(list, "试卷题目关联", ExamPaperQuestionVo.class, response);
    }

    /**
     * 获取试卷题目关联详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("exam:paperQuestion:query")
    @GetMapping("/{id}")
    public R<ExamPaperQuestionVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(iExamPaperQuestionService.queryById(id));
    }

    /**
     * 新增试卷题目关联
     */
    @SaCheckPermission("exam:paperQuestion:add")
    @Log(title = "试卷题目关联", businessType = BusinessType.INSERT)
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ExamPaperQuestionBo bo) {
        return toAjax(iExamPaperQuestionService.insertByBo(bo));
    }

    /**
     * 修改试卷题目关联
     */
    @SaCheckPermission("exam:paperQuestion:edit")
    @Log(title = "试卷题目关联", businessType = BusinessType.UPDATE)
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ExamPaperQuestionBo bo) {
        return toAjax(iExamPaperQuestionService.updateByBo(bo));
    }

    /**
     * 删除试卷题目关联
     *
     * @param ids 主键串
     */
    @SaCheckPermission("exam:paperQuestion:remove")
    @Log(title = "试卷题目关联", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return toAjax(iExamPaperQuestionService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
