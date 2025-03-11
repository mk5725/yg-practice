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
import com.ruoyi.exam.domain.vo.AnswerDetailVo;
import com.ruoyi.exam.domain.bo.AnswerDetailBo;
import com.ruoyi.exam.service.IAnswerDetailService;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

import java.util.List;
import java.util.Arrays;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.servlet.http.HttpServletResponse;

/**
 * 答题详情控制器
 * 前端访问路由地址为:/exam/detail
 *
 * @author zkm
 * @date  2025-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/detail")
public class AnswerDetailController extends BaseController {

    private final IAnswerDetailService iAnswerDetailService;

    /**
     * 查询答题详情列表
     */
    @SaCheckPermission("exam:detail:list")
    @GetMapping("/list")
    public TableDataInfo<AnswerDetailVo> list(AnswerDetailBo bo, PageQuery pageQuery) {
        return iAnswerDetailService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出答题详情列表
     */
    @SaCheckPermission("exam:detail:export")
    @Log(title = "答题详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(AnswerDetailBo bo, HttpServletResponse response) {
        List<AnswerDetailVo> list = iAnswerDetailService.queryList(bo);
        ExcelUtil.exportExcel(list, "答题详情", AnswerDetailVo.class, response);
    }

    /**
     * 获取答题详情详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("exam:detail:query")
    @GetMapping("/{id}")
    public R<AnswerDetailVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(iAnswerDetailService.queryById(id));
    }

    /**
     * 新增答题详情
     */
    @SaCheckPermission("exam:detail:add")
    @Log(title = "答题详情", businessType = BusinessType.INSERT)
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody AnswerDetailBo bo) {
        return toAjax(iAnswerDetailService.insertByBo(bo));
    }

    /**
     * 修改答题详情
     */
    @SaCheckPermission("exam:detail:edit")
    @Log(title = "答题详情", businessType = BusinessType.UPDATE)
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody AnswerDetailBo bo) {
        return toAjax(iAnswerDetailService.updateByBo(bo));
    }

    /**
     * 删除答题详情
     *
     * @param ids 主键串
     */
    @SaCheckPermission("exam:detail:remove")
    @Log(title = "答题详情", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return toAjax(iAnswerDetailService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
