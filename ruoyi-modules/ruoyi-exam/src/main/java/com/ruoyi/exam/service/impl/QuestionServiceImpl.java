package com.ruoyi.exam.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.exam.contants.ExamStatusConstants;
import com.ruoyi.exam.domain.ExamSend;
import com.ruoyi.exam.domain.enums.QuestionTypeEnum;
import com.ruoyi.exam.domain.vo.ExamPaperVo;
import com.ruoyi.exam.domain.vo.ExamSendVo;
import com.ruoyi.exam.exception.BusinessException;
import com.ruoyi.exam.exception.ErrorCode;
import com.ruoyi.exam.mapper.ExamPaperMapper;
import com.ruoyi.exam.mapper.ExamSendMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.exam.domain.bo.QuestionBo;
import com.ruoyi.exam.domain.vo.QuestionVo;
import com.ruoyi.exam.domain.Question;
import com.ruoyi.exam.mapper.QuestionMapper;
import com.ruoyi.exam.service.IQuestionService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 题目Service业务层处理
 *
 * @author zkm
 * @date 2025-03
 */
@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements IQuestionService {

    private final QuestionMapper baseMapper;
    private final ExamPaperMapper paperMapper;
    private final ExamSendMapper sendMapper;

    /**
     * 查询题目
     */
    @Override
    public QuestionVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询题目列表
     */
    @Override
    public TableDataInfo<QuestionVo> queryPageList(QuestionBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Question> lqw = buildQueryWrapper(bo);
        Page<QuestionVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询题目列表
     */
    @Override
    public List<QuestionVo> queryList(QuestionBo bo) {
        LambdaQueryWrapper<Question> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Question> buildQueryWrapper(QuestionBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Question> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getQuestionTypeCode() != null, Question::getQuestionTypeCode, bo.getQuestionTypeCode());
        lqw.like(StringUtils.isNotBlank(bo.getQuestionText()), Question::getQuestionText, bo.getQuestionText());
        lqw.eq(bo.getScore() != null, Question::getScore, bo.getScore());
        lqw.orderBy(true, false, Question::getUpdateTime);
        lqw.orderBy(true, false, Question::getCreateTime);
        return lqw;
    }

    /**
     * 新增题目
     */
    @Override
    public Boolean insertByBo(QuestionBo bo) {

        Question add = buildEntity(bo);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    // 构造实体对象
    private Question buildEntity(QuestionBo bo) {
        Question entity = BeanUtil.copyProperties(bo, Question.class);
        // 校验题型
        Integer typeCode = entity.getQuestionTypeCode();
        QuestionTypeEnum.fromCode(typeCode);
        return entity;
    }

    /**
     * 修改题目
     */
    @Override
    @Transactional
    public Boolean updateByBo(QuestionBo bo) {
        Question update = buildEntity(bo);

        // 查询题目是否被试卷引用
        List<ExamPaperVo> relatedPapers = paperMapper.selectByQuestionId(bo.getId());

        if (relatedPapers.isEmpty()) {
            // 题目未被试卷引用，直接修改
            return baseMapper.updateById(update) > 0;
        }

        // 考试未结束
        boolean hasOngoingExam = isHasOngoingExam(relatedPapers);
        if (hasOngoingExam) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "当前题目正在考试中，无法修改！");
        }

        // 考试已结束，逻辑删除旧题目，新增新题目
        baseMapper.deleteById(bo.getId());
        update.setId(null);
        return baseMapper.insert(update) > 0;
    }


    /**
     * 批量删除题目
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {

        // 查询题目是否被试卷引用
        List<ExamPaperVo> relatedPapers = paperMapper.selectByQuestionIds(ids);

        // 判断是否在考试中
        boolean hasOngoingExam = !relatedPapers.isEmpty() && isHasOngoingExam(relatedPapers);
        if (hasOngoingExam) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在考试中，无法删除！");
        }

        return baseMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 判断发放考试是否结束
     * @param relatedPapers 试卷信息
     * @return true 考试中
     */
    private boolean isHasOngoingExam(List<ExamPaperVo> relatedPapers) {
        List<Long> collect = relatedPapers.stream().map(ExamPaperVo::getId).collect(Collectors.toList());

        List<ExamSendVo> sendVoList = sendMapper.selectVoList(Wrappers.<ExamSend>lambdaQuery()
            .in(ExamSend::getPaperId, collect));
        return sendVoList.stream()
            .anyMatch(send -> send.getStatus() != ExamStatusConstants.STATUS_COMPLETED);
    }
}
