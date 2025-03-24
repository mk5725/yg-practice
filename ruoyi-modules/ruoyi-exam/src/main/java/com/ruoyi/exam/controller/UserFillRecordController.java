package com.ruoyi.exam.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.excel.utils.ExcelUtil;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.common.satoken.utils.LoginHelper;
import com.ruoyi.exam.domain.bo.ExamPaperBo;
import com.ruoyi.exam.domain.bo.ExamRecordBo;
import com.ruoyi.exam.domain.vo.ExamPaperVo;
import com.ruoyi.exam.domain.vo.ExamRecordVo;
import com.ruoyi.exam.service.IExamRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * 用户考试记录
 * 前端访问路由地址为:/exam/fill
 *
 * @author zkm
 * @date 2025-03-15
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/fill")
public class UserFillRecordController extends BaseController {

    private final IExamRecordService iExamRecordService;

    /**
     * 查询当前登录用户考试记录
     */
    @SaCheckPermission("exam:record:list")
    @GetMapping("/record")
    public TableDataInfo<ExamRecordVo> list(ExamRecordBo bo, PageQuery pageQuery) {
        bo.setUserId(LoginHelper.getUserId());
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
     * 根据考试记录ID 获取试卷题目 - 考试
     *
     * @param id 主键
     */
    @SaCheckPermission("exam:record:query")
    @GetMapping("/record/{id}")
    public R<ExamPaperVo> getPaperQuestion(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(iExamRecordService.queryById(id));
    }

    /**
     * 根据考试记录ID 获取试卷全部信息（标答、解析、考生答案） - 批改信息
     *
     * @param id 主键
     */
    @SaCheckPermission("exam:record:query")
    @GetMapping("/review/{id}")
    public R<ExamPaperVo> getPaperReviewer(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(iExamRecordService.queryPaperReviewer(id));
    }

    /**
     * 交卷 - 提交答案
     */
    @SaCheckPermission("exam:record:add")
    @PostMapping("/submit/paper")
    public R<Void> submitExamPaper(@RequestBody ExamPaperBo bo) {
        return toAjax(iExamRecordService.submitExamPaper(bo));
    }

    /**
     *  提交批改
     */
    @SaCheckPermission("exam:record:add")
    @PostMapping("/submit/review")
    public R<Void> submitReviewPaper(@RequestBody @NotNull ExamRecordBo bo) {
        return toAjax(iExamRecordService.submitReviewPaper(bo));
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
