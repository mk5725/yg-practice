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
import com.ruoyi.exam.domain.bo.AnswerDetailBo;
import com.ruoyi.exam.domain.vo.AnswerDetailVo;
import com.ruoyi.exam.domain.AnswerDetail;
import com.ruoyi.exam.mapper.AnswerDetailMapper;
import com.ruoyi.exam.service.IAnswerDetailService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 答题详情Service业务层处理
 *
 * @author zkm
 * @date  2025-03
 */
@RequiredArgsConstructor
@Service
public class AnswerDetailServiceImpl implements IAnswerDetailService {

    private final AnswerDetailMapper baseMapper;

    /**
     * 查询答题详情
     */
    @Override
    public AnswerDetailVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询答题详情列表
     */
    @Override
    public TableDataInfo<AnswerDetailVo> queryPageList(AnswerDetailBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<AnswerDetail> lqw = buildQueryWrapper(bo);
        Page<AnswerDetailVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询答题详情列表
     */
    @Override
    public List<AnswerDetailVo> queryList(AnswerDetailBo bo) {
        LambdaQueryWrapper<AnswerDetail> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<AnswerDetail> buildQueryWrapper(AnswerDetailBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<AnswerDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getPaperId() != null, AnswerDetail::getPaperId, bo.getPaperId());
        lqw.eq(bo.getUserId() != null, AnswerDetail::getUserId, bo.getUserId());
        lqw.eq(bo.getExamSendId() != null, AnswerDetail::getExamSendId, bo.getExamSendId());
        lqw.eq(bo.getQuestionId() != null, AnswerDetail::getQuestionId, bo.getQuestionId());
        lqw.eq(StringUtils.isNotBlank(bo.getUserAnswer()), AnswerDetail::getUserAnswer, bo.getUserAnswer());
        lqw.eq(bo.getOkFlag() != null, AnswerDetail::getOkFlag, bo.getOkFlag());
        lqw.eq(bo.getScore() != null, AnswerDetail::getScore, bo.getScore());
        return lqw;
    }

    /**
     * 新增答题详情
     */
    @Override
    public Boolean insertByBo(AnswerDetailBo bo) {
        AnswerDetail add = BeanUtil.toBean(bo, AnswerDetail.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改答题详情
     */
    @Override
    public Boolean updateByBo(AnswerDetailBo bo) {
        AnswerDetail update = BeanUtil.toBean(bo, AnswerDetail.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(AnswerDetail entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除答题详情
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
