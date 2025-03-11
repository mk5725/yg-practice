package com.ruoyi.exam.service;

import com.ruoyi.exam.domain.ExamPaperTitle;
import com.ruoyi.exam.domain.vo.ExamPaperTitleVo;
import com.ruoyi.exam.domain.bo.ExamPaperTitleBo;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 试卷标题Service接口
 *
 * @author zkm
 * @date 2025-03-11
 */
public interface IExamPaperTitleService {

    /**
     * 查询试卷标题
     */
    ExamPaperTitleVo queryById(Long id);

    /**
     * 查询试卷标题列表
     */
    TableDataInfo<ExamPaperTitleVo> queryPageList(ExamPaperTitleBo bo, PageQuery pageQuery);

    /**
     * 查询试卷标题列表
     */
    List<ExamPaperTitleVo> queryList(ExamPaperTitleBo bo);

    /**
     * 修改试卷标题
     */
    Boolean insertByBo(ExamPaperTitleBo bo);

    /**
     * 修改试卷标题
     */
    Boolean updateByBo(ExamPaperTitleBo bo);

    /**
     * 校验并批量删除试卷标题信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
