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
import com.ruoyi.exam.domain.bo.ExamCountBo;
import com.ruoyi.exam.domain.vo.ExamCountVo;
import com.ruoyi.exam.domain.ExamCount;
import com.ruoyi.exam.mapper.ExamCountMapper;
import com.ruoyi.exam.service.IExamCountService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 成绩统计Service业务层处理
 *
 * @author zkm
 * @date  2025-03
 */
@RequiredArgsConstructor
@Service
public class ExamCountServiceImpl implements IExamCountService {

    private final ExamCountMapper baseMapper;

    /**
     * 查询成绩统计
     */
    @Override
    public ExamCountVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询成绩统计列表
     */
    @Override
    public TableDataInfo<ExamCountVo> queryPageList(ExamCountBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ExamCount> lqw = buildQueryWrapper(bo);
        Page<ExamCountVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询成绩统计列表
     */
    @Override
    public List<ExamCountVo> queryList(ExamCountBo bo) {
        LambdaQueryWrapper<ExamCount> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ExamCount> buildQueryWrapper(ExamCountBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ExamCount> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getPaperId() != null, ExamCount::getPaperId, bo.getPaperId());
        lqw.eq(bo.getAvgScore() != null, ExamCount::getAvgScore, bo.getAvgScore());
        lqw.eq(bo.getMaxScore() != null, ExamCount::getMaxScore, bo.getMaxScore());
        lqw.eq(bo.getMinScore() != null, ExamCount::getMinScore, bo.getMinScore());
        lqw.eq(bo.getPassRate() != null, ExamCount::getPassRate, bo.getPassRate());
        lqw.eq(bo.getCreateTime() != null, ExamCount::getCreateTime, bo.getCreateTime());
        return lqw;
    }

    /**
     * 新增成绩统计
     */
    @Override
    public Boolean insertByBo(ExamCountBo bo) {
        ExamCount add = BeanUtil.toBean(bo, ExamCount.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改成绩统计
     */
    @Override
    public Boolean updateByBo(ExamCountBo bo) {
        ExamCount update = BeanUtil.toBean(bo, ExamCount.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ExamCount entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除成绩统计
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
