package com.ruoyi.exam.mapper;

import com.ruoyi.common.mybatis.core.mapper.BaseMapperPlus;
import com.ruoyi.exam.domain.ExamPaper;
import com.ruoyi.exam.domain.dto.ExamPaperData;
import com.ruoyi.exam.domain.dto.QuestionDto;
import com.ruoyi.exam.domain.vo.ExamPaperVo;
import com.ruoyi.exam.domain.vo.ExamSendVo;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 试卷Mapper接口
 *
 * @author zkm
 * @date  2025-03
 */
public interface ExamPaperMapper extends BaseMapperPlus<ExamPaperMapper, ExamPaper, ExamPaperVo> {

    /**
     * 根据试卷ID查询完整的试卷信息（包含标题、题目）
     */
    ExamPaperVo selectCompleteExamPaperById(@Param("paperId") Long paperId);

    /**
     * 根据试卷ID查询试卷信息
     */
    ExamPaper selectExamPaperById(@Param("paperId") Long paperId);

    /**
     * 查询试卷标题信息
     */
    List<ExamPaperData> selectExamPaperTitlesByPaperId(@Param("paperId") Long paperId);

    /**
     * 查询试卷标题下的题目信息
     */
    List<QuestionDto> selectQuestionsByTitleId(@Param("titleId") Long titleId);

    /**
     * 查询题目是否被试卷引用
     * @param id
     * @return
     */
    List<ExamPaperVo> selectByQuestionId(@Param("questionId") Long id);

    /**
     * 查询题目是否被试卷引用
     * @param ids
     * @return
     */
    List<ExamPaperVo> selectByQuestionIds(@Param("ids") Collection<Long> ids);
}
