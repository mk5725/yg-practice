package com.ruoyi.exam.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.exam.domain.bo.ExamPaperQuestionBo;
import com.ruoyi.exam.domain.vo.ExamPaperQuestionVo;
import com.ruoyi.exam.domain.ExamPaperQuestion;
import com.ruoyi.exam.mapper.ExamPaperQuestionMapper;
import com.ruoyi.exam.service.IExamPaperQuestionService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 试卷题目关联Service业务层处理
 *
 * @author zkm
 * @date  2025-03
 */
@RequiredArgsConstructor
@Service
public class ExamPaperQuestionServiceImpl implements IExamPaperQuestionService {

    private final ExamPaperQuestionMapper baseMapper;

    /**
     * 查询试卷题目关联
     */
    @Override
    public ExamPaperQuestionVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询试卷题目关联列表
     */
    @Override
    public TableDataInfo<ExamPaperQuestionVo> queryPageList(ExamPaperQuestionBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ExamPaperQuestion> lqw = buildQueryWrapper(bo);
        Page<ExamPaperQuestionVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询试卷题目关联列表
     */
    @Override
    public List<ExamPaperQuestionVo> queryList(ExamPaperQuestionBo bo) {
        LambdaQueryWrapper<ExamPaperQuestion> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ExamPaperQuestion> buildQueryWrapper(ExamPaperQuestionBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ExamPaperQuestion> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getPaperId() != null, ExamPaperQuestion::getPaperId, bo.getPaperId());
        lqw.eq(bo.getQuestionId() != null, ExamPaperQuestion::getQuestionId, bo.getQuestionId());
        lqw.eq(bo.getScore() != null, ExamPaperQuestion::getScore, bo.getScore());
        lqw.eq(bo.getCreateTime() != null, ExamPaperQuestion::getCreateTime, bo.getCreateTime());
        return lqw;
    }

    /**
     * 新增试卷题目关联
     */
    @Override
    public Boolean insertByBo(ExamPaperQuestionBo bo) {
        ExamPaperQuestion add = BeanUtil.toBean(bo, ExamPaperQuestion.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改试卷题目关联
     */
    @Override
    public Boolean updateByBo(ExamPaperQuestionBo bo) {
        ExamPaperQuestion update = BeanUtil.toBean(bo, ExamPaperQuestion.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ExamPaperQuestion entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除试卷题目关联
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
