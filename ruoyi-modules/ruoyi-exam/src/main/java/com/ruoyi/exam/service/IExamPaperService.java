package com.ruoyi.exam.service;

import com.ruoyi.exam.domain.ExamPaper;
import com.ruoyi.exam.domain.vo.ExamPaperVo;
import com.ruoyi.exam.domain.bo.ExamPaperBo;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 试卷Service接口
 *
 * @author zkm
 * @date  2025-03
 */
public interface IExamPaperService {

    /**
     * 查询试卷
     */
    ExamPaperVo queryById(Long id);

    /**
     * 查询试卷列表
     */
    TableDataInfo<ExamPaperVo> queryPageList(ExamPaperBo bo, PageQuery pageQuery);

    /**
     * 查询试卷列表
     */
    List<ExamPaperVo> queryList(ExamPaperBo bo);

    /**
     * 修改试卷
     */
    Boolean insertByBo(ExamPaperBo bo);

    /**
     * 修改试卷
     */
    Boolean updateByBo(ExamPaperBo bo);

    /**
     * 校验并批量删除试卷信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
