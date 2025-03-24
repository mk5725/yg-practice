package com.ruoyi.exam.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.exam.contants.ExamStatusConstants;
import com.ruoyi.exam.domain.ExamPaperQuestion;
import com.ruoyi.exam.domain.ExamPaperTitle;
import com.ruoyi.exam.domain.ExamSend;
import com.ruoyi.exam.domain.bo.ExamPaperTitleBo;
import com.ruoyi.exam.domain.bo.QuestionBo;
import com.ruoyi.exam.domain.vo.ExamSendVo;
import com.ruoyi.exam.exception.BusinessException;
import com.ruoyi.exam.exception.ErrorCode;
import com.ruoyi.exam.mapper.ExamPaperQuestionMapper;
import com.ruoyi.exam.mapper.ExamPaperTitleMapper;
import com.ruoyi.exam.mapper.ExamSendMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.exam.domain.bo.ExamPaperBo;
import com.ruoyi.exam.domain.vo.ExamPaperVo;
import com.ruoyi.exam.domain.ExamPaper;
import com.ruoyi.exam.mapper.ExamPaperMapper;
import com.ruoyi.exam.service.IExamPaperService;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 试卷Service业务层处理
 *
 * @author zkm
 * @date 2025-03
 */
@RequiredArgsConstructor
@Service
public class ExamPaperServiceImpl implements IExamPaperService {

    private final ExamPaperMapper baseMapper;

    private final ExamPaperTitleMapper titleMapper;

    private final ExamPaperQuestionMapper paperQuestionMapper;

    private final ExamSendMapper sendMapper;

    /**
     * 查询完整试卷信息 （包含标题、题目）
     */
    @Override
    public ExamPaperVo queryById(Long id) {

        ExamPaperVo examPaperVo = baseMapper.selectCompleteExamPaperById(id);

        return examPaperVo;
    }

    /**
     * 查询试卷列表
     */
    @Override
    public TableDataInfo<ExamPaperVo> queryPageList(ExamPaperBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ExamPaper> lqw = buildQueryWrapper(bo);
        Page<ExamPaperVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询试卷列表
     */
    @Override
    public List<ExamPaperVo> queryList(ExamPaperBo bo) {
        LambdaQueryWrapper<ExamPaper> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ExamPaper> buildQueryWrapper(ExamPaperBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ExamPaper> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getPaperName()), ExamPaper::getPaperName, bo.getPaperName());
        lqw.eq(StringUtils.isNotBlank(bo.getPaperType()), ExamPaper::getPaperType, bo.getPaperType());
        lqw.eq(bo.getDuration() != null, ExamPaper::getDuration, bo.getDuration());
        lqw.eq(bo.getTotalScore() != null, ExamPaper::getTotalScore, bo.getTotalScore());
        lqw.eq(bo.getPassScore() != null, ExamPaper::getPassScore, bo.getPassScore());
        lqw.eq(bo.getCreateTime() != null, ExamPaper::getCreateTime, bo.getCreateTime());
        lqw.orderBy(true, false, ExamPaper::getCreateTime);
        return lqw;
    }

    /**
     * 修改试卷
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateByBo(ExamPaperBo bo) {
        Long paperId = bo.getId();

        // 判断试卷是否被发放
        List<ExamSendVo> sendVoList = sendMapper.selectSendByPaperId(paperId);
        if (ObjectUtil.isNotEmpty(sendVoList)) {
            // 判断发放考试是否结束
            boolean hasDoing = sendVoList.stream()
                .anyMatch(send -> send.getStatus() != ExamStatusConstants.STATUS_COMPLETED);

            // 考试未结束，禁止修改
            if (hasDoing) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "试卷正在考试中，无法修改！");
            }

            // 重新新增试卷，保留历史试卷信息
            baseMapper.deleteById(bo.getId());
            bo.setId(null);
        } else {
            // 删除试卷标题旧数据
            titleMapper.delete(Wrappers.<ExamPaperTitle>lambdaQuery()
                .eq(ExamPaperTitle::getPaperId, paperId));

            // 删除试卷题目旧数据
            paperQuestionMapper.delete(Wrappers.<ExamPaperQuestion>lambdaQuery()
                .eq(ExamPaperQuestion::getPaperId, paperId));
        }

        return insertOrUpdate(bo);
    }

    /**
     * 新增、更新试卷
     */
    @Override
    @Transactional
    public Boolean insertOrUpdate(ExamPaperBo bo) {
        ExamPaper entity = BeanUtil.toBean(bo, ExamPaper.class);

        if (!baseMapper.insertOrUpdate(entity)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }

        long paperId = entity.getId();
        // 获取标题 - 题目数据列表
        List<ExamPaperTitleBo> paperDataList = bo.getPaperTitleList();
        List<ExamPaperTitle> examPaperTitleList = new ArrayList<>();
        List<ExamPaperQuestion> examPaperQuestionList = new ArrayList<>();

        if (paperDataList != null && !paperDataList.isEmpty()) {
            for (ExamPaperTitleBo titleBo : paperDataList) {
                // 生成标题ID
                long titleId = IdUtil.getSnowflakeNextId();

                // 构造标题数据 实体对象
                ExamPaperTitle title = ExamPaperTitle.builder()
                    .id(titleId)
                    .paperId(paperId)
                    .orderNum(titleBo.getOrderNum())
                    .titleName(titleBo.getTitleName())
                    .build();
                examPaperTitleList.add(title);

                int i = 0;
                if (titleBo.getQuestionList() != null && !titleBo.getQuestionList().isEmpty()) {
                    for (QuestionBo question : titleBo.getQuestionList()) {
                        // 构造试卷题目关联 实体对象
                        ExamPaperQuestion paperQuestion = ExamPaperQuestion.builder()
                            .titleId(titleId)
                            .paperId(paperId)
                            .questionId(question.getId())
                            .orderSum(i++)
                            .build();
                        examPaperQuestionList.add(paperQuestion);
                    }
                }
            }
        }
        // 添加标题数据
        if (!examPaperTitleList.isEmpty()) {
            titleMapper.insertBatch(examPaperTitleList);
        }
        // 添加试卷题目数据
        if (!examPaperQuestionList.isEmpty()) {
            paperQuestionMapper.insertBatch(examPaperQuestionList);
        }
        return true;
    }

    /**
     * 批量删除试卷
     *
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        // 判断试卷是否被发放
        List<ExamSendVo> sendVoList = sendMapper.selectVoList(Wrappers.<ExamSend>lambdaQuery()
            .in(ExamSend::getPaperId, ids));

        if (ObjectUtil.isNotEmpty(sendVoList)) {
            boolean hasDoing = sendVoList.stream()
                .anyMatch(send -> send.getStatus() != ExamStatusConstants.STATUS_COMPLETED);
            if (hasDoing) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "试卷正在考试中，无法删除!");
            }
        }else {
            // 删除试卷标题
            titleMapper.delete(Wrappers.<ExamPaperTitle>lambdaQuery()
                .in(ExamPaperTitle::getPaperId, ids));
            // 删除试卷题目
            paperQuestionMapper.delete(Wrappers.<ExamPaperQuestion>lambdaQuery()
                .in(ExamPaperQuestion::getPaperId, ids));
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public boolean existsById(Long examId) {
        if (examId == null) {
            return false;
        }
        return baseMapper.exists(Wrappers.<ExamPaper>lambdaQuery()
            .eq(ExamPaper::getId, examId));
    }

    @Override
    public boolean existsWithLock(Long examId) {
        if (examId == null) {
            return false;
        }
        Long count = baseMapper.selectCount(
            Wrappers.<ExamPaper>lambdaQuery()
                .eq(ExamPaper::getId, examId)
                .last("FOR UPDATE"));
        return count != null && count > 0;
    }
}
