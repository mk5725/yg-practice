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
import com.ruoyi.exam.domain.vo.QuestionVo;
import com.ruoyi.exam.domain.bo.QuestionBo;
import com.ruoyi.exam.service.IQuestionService;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

import java.util.List;
import java.util.Arrays;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.servlet.http.HttpServletResponse;

/**
 * 题目控制器
 * 前端访问路由地址为:/exam/question
 *
 * @author zkm
 * @date  2025-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/question")
public class QuestionController extends BaseController {

    private final IQuestionService iQuestionService;

    /**
     * 查询题目列表
     */
    @SaCheckPermission("exam:question:list")
    @GetMapping("/list")
    public TableDataInfo<QuestionVo> list(QuestionBo bo, PageQuery pageQuery) {
        return iQuestionService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出题目列表
     */
    @SaCheckPermission("exam:question:export")
    @Log(title = "题目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(QuestionBo bo, HttpServletResponse response) {
        List<QuestionVo> list = iQuestionService.queryList(bo);
        ExcelUtil.exportExcel(list, "题目", QuestionVo.class, response);
    }

    /**
     * 获取题目详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("exam:question:query")
    @GetMapping("/{id}")
    public R<QuestionVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(iQuestionService.queryById(id));
    }

    /**
     * 新增题目
     */
    @SaCheckPermission("exam:question:add")
    @Log(title = "题目", businessType = BusinessType.INSERT)
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody QuestionBo bo) {
        return toAjax(iQuestionService.insertByBo(bo));
    }

    /**
     * 修改题目
     */
    @SaCheckPermission("exam:question:edit")
    @Log(title = "题目", businessType = BusinessType.UPDATE)
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody QuestionBo bo) {
        return toAjax(iQuestionService.updateByBo(bo));
    }

    /**
     * 删除题目
     *
     * @param ids 主键串
     */
    @SaCheckPermission("exam:question:remove")
    @Log(title = "题目", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return toAjax(iQuestionService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
