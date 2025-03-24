package com.ruoyi.exam.service;

import com.ruoyi.exam.domain.ExamRecord;
import com.ruoyi.exam.domain.bo.ExamPaperBo;
import com.ruoyi.exam.domain.vo.ExamPaperVo;
import com.ruoyi.exam.domain.vo.ExamRecordVo;
import com.ruoyi.exam.domain.bo.ExamRecordBo;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 考试记录信息Service接口
 *
 * @author zkm
 * @date 2025-03-15
 */
public interface IExamRecordService {

    /**
     * 查询考试记录信息
     */
    ExamPaperVo queryById(Long id);

    ExamPaperVo queryPaperReviewer(Long id);

    /**
     * 查询考试记录信息列表
     */
    TableDataInfo<ExamRecordVo> queryPageList(ExamRecordBo bo, PageQuery pageQuery);

    /**
     * 查询考试记录信息列表
     */
    List<ExamRecordVo> queryList(ExamRecordBo bo);

    /**
     * 修改考试记录信息
     */
    Boolean insertByBo(ExamRecordBo bo);

    /**
     * 修改考试记录信息
     */
    Boolean updateByBo(ExamRecordBo bo);

    /**
     * 校验并批量删除考试记录信息信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 交卷
     */
    boolean submitExamPaper(ExamPaperBo bo);

    /**
     * 批改
     */
    boolean submitReviewPaper(ExamRecordBo bo);
}
