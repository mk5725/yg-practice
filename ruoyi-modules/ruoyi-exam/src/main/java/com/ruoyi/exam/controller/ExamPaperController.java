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
import com.ruoyi.exam.domain.vo.ExamPaperVo;
import com.ruoyi.exam.domain.bo.ExamPaperBo;
import com.ruoyi.exam.service.IExamPaperService;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

import java.util.List;
import java.util.Arrays;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.servlet.http.HttpServletResponse;

/**
 * 试卷控制器
 * 前端访问路由地址为:/exam/paper
 *
 * @author zkm
 * @date  2025-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/paper")
public class ExamPaperController extends BaseController {

    private final IExamPaperService iExamPaperService;

    /**
     * 查询试卷列表
     */
    @SaCheckPermission("exam:paper:list")
    @GetMapping("/list")
    public TableDataInfo<ExamPaperVo> list(ExamPaperBo bo, PageQuery pageQuery) {
        return iExamPaperService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出试卷列表
     */
    @SaCheckPermission("exam:paper:export")
    @Log(title = "试卷", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ExamPaperBo bo, HttpServletResponse response) {
        List<ExamPaperVo> list = iExamPaperService.queryList(bo);
        ExcelUtil.exportExcel(list, "试卷", ExamPaperVo.class, response);
    }

    /**
     * 获取试卷详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("exam:paper:query")
    @GetMapping("/{id}")
    public R<ExamPaperVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(iExamPaperService.queryById(id));
    }

    /**
     * 新增试卷
     */
    @SaCheckPermission("exam:paper:add")
    @Log(title = "试卷", businessType = BusinessType.INSERT)
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ExamPaperBo bo) {
        return toAjax(iExamPaperService.insertByBo(bo));
    }

    /**
     * 修改试卷
     */
    @SaCheckPermission("exam:paper:edit")
    @Log(title = "试卷", businessType = BusinessType.UPDATE)
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ExamPaperBo bo) {
        return toAjax(iExamPaperService.updateByBo(bo));
    }

    /**
     * 删除试卷
     *
     * @param ids 主键串
     */
    @SaCheckPermission("exam:paper:remove")
    @Log(title = "试卷", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return toAjax(iExamPaperService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
