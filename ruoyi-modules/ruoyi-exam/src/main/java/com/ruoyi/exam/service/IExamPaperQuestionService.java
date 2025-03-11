package com.ruoyi.exam.service;

import com.ruoyi.exam.domain.ExamPaperQuestion;
import com.ruoyi.exam.domain.vo.ExamPaperQuestionVo;
import com.ruoyi.exam.domain.bo.ExamPaperQuestionBo;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 试卷题目关联Service接口
 *
 * @author zkm
 * @date  2025-03
 */
public interface IExamPaperQuestionService {

    /**
     * 查询试卷题目关联
     */
    ExamPaperQuestionVo queryById(Long id);

    /**
     * 查询试卷题目关联列表
     */
    TableDataInfo<ExamPaperQuestionVo> queryPageList(ExamPaperQuestionBo bo, PageQuery pageQuery);

    /**
     * 查询试卷题目关联列表
     */
    List<ExamPaperQuestionVo> queryList(ExamPaperQuestionBo bo);

    /**
     * 修改试卷题目关联
     */
    Boolean insertByBo(ExamPaperQuestionBo bo);

    /**
     * 修改试卷题目关联
     */
    Boolean updateByBo(ExamPaperQuestionBo bo);

    /**
     * 校验并批量删除试卷题目关联信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
