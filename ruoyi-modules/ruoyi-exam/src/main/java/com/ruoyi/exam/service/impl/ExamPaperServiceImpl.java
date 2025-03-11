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
import com.ruoyi.exam.domain.bo.ExamPaperBo;
import com.ruoyi.exam.domain.vo.ExamPaperVo;
import com.ruoyi.exam.domain.ExamPaper;
import com.ruoyi.exam.mapper.ExamPaperMapper;
import com.ruoyi.exam.service.IExamPaperService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 试卷Service业务层处理
 *
 * @author zkm
 * @date  2025-03
 */
@RequiredArgsConstructor
@Service
public class ExamPaperServiceImpl implements IExamPaperService {

    private final ExamPaperMapper baseMapper;

    /**
     * 查询试卷
     */
    @Override
    public ExamPaperVo queryById(Long id){
        return baseMapper.selectVoById(id);
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
     * 新增试卷
     */
    @Override
    public Boolean insertByBo(ExamPaperBo bo) {
        ExamPaper add = BeanUtil.toBean(bo, ExamPaper.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改试卷
     */
    @Override
    public Boolean updateByBo(ExamPaperBo bo) {
        ExamPaper update = BeanUtil.toBean(bo, ExamPaper.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ExamPaper entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除试卷
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
