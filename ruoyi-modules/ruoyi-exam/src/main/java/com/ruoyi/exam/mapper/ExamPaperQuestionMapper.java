package com.ruoyi.exam.mapper;

import com.ruoyi.common.mybatis.core.mapper.BaseMapperPlus;
import com.ruoyi.exam.domain.ExamPaperQuestion;
import com.ruoyi.exam.domain.vo.ExamPaperQuestionVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 试卷题目关联Mapper接口
 *
 * @author zkm
 * @date  2025-03
 */
public interface ExamPaperQuestionMapper extends BaseMapperPlus<ExamPaperQuestionMapper, ExamPaperQuestion, ExamPaperQuestionVo> {

    /**
     * 查询试卷题目数量
     */
    List<ExamPaperQuestionVo> countPaperWithQuestion(@Param("paperId") Long paperId);

}
