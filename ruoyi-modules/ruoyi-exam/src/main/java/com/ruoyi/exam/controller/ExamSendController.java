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
import com.ruoyi.exam.domain.vo.ExamSendVo;
import com.ruoyi.exam.domain.bo.ExamSendBo;
import com.ruoyi.exam.service.IExamSendService;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

import java.util.List;
import java.util.Arrays;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.servlet.http.HttpServletResponse;

/**
 * 试卷发放记录控制器
 * 前端访问路由地址为:/exam/send
 *
 * @author zkm
 * @date  2025-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/send")
public class ExamSendController extends BaseController {

    private final IExamSendService iExamSendService;

    /**
     * 查询试卷发放记录列表
     */
    @SaCheckPermission("exam:send:list")
    @GetMapping("/list")
    public TableDataInfo<ExamSendVo> list(ExamSendBo bo, PageQuery pageQuery) {
        return iExamSendService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出试卷发放记录列表
     */
    @SaCheckPermission("exam:send:export")
    @Log(title = "试卷发放记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ExamSendBo bo, HttpServletResponse response) {
        List<ExamSendVo> list = iExamSendService.queryList(bo);
        ExcelUtil.exportExcel(list, "试卷发放记录", ExamSendVo.class, response);
    }

    /**
     * 获取试卷发放记录详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("exam:send:query")
    @GetMapping("/{id}")
    public R<ExamSendVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(iExamSendService.queryById(id));
    }

    /**
     * 新增试卷发放记录
     */
    @SaCheckPermission("exam:send:add")
    @Log(title = "试卷发放记录", businessType = BusinessType.INSERT)
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ExamSendBo bo) {
        return toAjax(iExamSendService.insertByBo(bo));
    }

    /**
     * 修改试卷发放记录
     */
    @SaCheckPermission("exam:send:edit")
    @Log(title = "试卷发放记录", businessType = BusinessType.UPDATE)
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ExamSendBo bo) {
        return toAjax(iExamSendService.updateByBo(bo));
    }

    /**
     * 删除试卷发放记录
     *
     * @param ids 主键串
     */
    @SaCheckPermission("exam:send:remove")
    @Log(title = "试卷发放记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return toAjax(iExamSendService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
