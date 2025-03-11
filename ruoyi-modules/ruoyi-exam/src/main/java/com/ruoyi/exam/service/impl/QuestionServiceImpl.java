package com.ruoyi.exam.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.exam.domain.ExamPaper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ruoyi.exam.domain.bo.QuestionBo;
import com.ruoyi.exam.domain.vo.QuestionVo;
import com.ruoyi.exam.domain.Question;
import com.ruoyi.exam.mapper.QuestionMapper;
import com.ruoyi.exam.service.IQuestionService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 题目Service业务层处理
 *
 * @author zkm
 * @date  2025-03
 */
@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements IQuestionService {

    private final QuestionMapper baseMapper;

    /**
     * 查询题目
     */
    @Override
    public QuestionVo queryById(Long id){
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
        lqw.orderBy(true, false, Question::getCreateTime);
        return lqw;
    }

    /**
     * 新增题目
     */
    @Override
    public Boolean insertByBo(QuestionBo bo) {
        Question add = BeanUtil.toBean(bo, Question.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改题目
     */
    @Override
    public Boolean updateByBo(QuestionBo bo) {
        Question update = BeanUtil.toBean(bo, Question.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Question entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除题目
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
