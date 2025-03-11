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
import org.redisson.api.RList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.exam.domain.vo.QuestionTypeVo;
import com.ruoyi.exam.domain.bo.QuestionTypeBo;
import com.ruoyi.exam.service.IQuestionTypeService;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

import java.util.List;
import java.util.Arrays;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.servlet.http.HttpServletResponse;

/**
 * 题目类型控制器
 * 前端访问路由地址为:/exam/questionType
 *
 * @author zkm
 * @date 2025-03-11
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/questionType")
public class QuestionTypeController extends BaseController {

    private final IQuestionTypeService iQuestionTypeService;

    /**
     * 查询题目类型列表
     */
    @SaCheckPermission("exam:questionType:list")
    @GetMapping("/list")
    public TableDataInfo<QuestionTypeVo> list(QuestionTypeBo bo, PageQuery pageQuery) {
        return iQuestionTypeService.queryPageList(bo, pageQuery);
    }


    /**
     * 查询全部题目类型列表
     */
    @SaCheckPermission("exam:questionType:list")
    @GetMapping("/list/all")
    public R<List<QuestionTypeVo>> listAll(QuestionTypeBo bo) {
        return R.ok(iQuestionTypeService.queryListAll(bo));
    }

    /**
     * 导出题目类型列表
     */
    @SaCheckPermission("exam:questionType:export")
    @Log(title = "题目类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(QuestionTypeBo bo, HttpServletResponse response) {
        List<QuestionTypeVo> list = iQuestionTypeService.queryList(bo);
        ExcelUtil.exportExcel(list, "题目类型", QuestionTypeVo.class, response);
    }

    /**
     * 获取题目类型详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("exam:questionType:query")
    @GetMapping("/{id}")
    public R<QuestionTypeVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(iQuestionTypeService.queryById(id));
    }

    /**
     * 新增题目类型
     */
    @SaCheckPermission("exam:questionType:add")
    @Log(title = "题目类型", businessType = BusinessType.INSERT)
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody QuestionTypeBo bo) {
        return toAjax(iQuestionTypeService.insertByBo(bo));
    }

    /**
     * 修改题目类型
     */
    @SaCheckPermission("exam:questionType:edit")
    @Log(title = "题目类型", businessType = BusinessType.UPDATE)
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody QuestionTypeBo bo) {
        return toAjax(iQuestionTypeService.updateByBo(bo));
    }

    /**
     * 删除题目类型
     *
     * @param ids 主键串
     */
    @SaCheckPermission("exam:questionType:remove")
    @Log(title = "题目类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return toAjax(iQuestionTypeService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
