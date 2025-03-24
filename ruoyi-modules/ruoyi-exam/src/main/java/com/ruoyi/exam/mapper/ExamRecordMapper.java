package com.ruoyi.exam.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.mybatis.core.mapper.BaseMapperPlus;
import com.ruoyi.exam.domain.ExamRecord;
import com.ruoyi.exam.domain.bo.ExamRecordBo;
import com.ruoyi.exam.domain.vo.ExamPaperVo;
import com.ruoyi.exam.domain.vo.ExamRecordVo;
import org.apache.ibatis.annotations.Param;

/**
 * 考试记录信息Mapper接口
 *
 * @author zkm
 * @date 2025-03-15
 */
public interface ExamRecordMapper extends BaseMapperPlus<ExamRecordMapper, ExamRecord, ExamRecordVo> {
    /**
     * 考试记录
     */
    Page<ExamRecordVo> selectRecordVoPage(@Param("page") Page page, @Param("bo") ExamRecordBo bo);

    /**
     * 根据记录ID 查询试卷题目 -- 考生考试
     */
    ExamPaperVo selectExamPaperById(@Param("id") Long id);

    /**
     * 根据记录ID 查询试卷信息 -- 试卷批改
     */
    ExamPaperVo selectExamReviewerById(@Param("id") Long id);
}
