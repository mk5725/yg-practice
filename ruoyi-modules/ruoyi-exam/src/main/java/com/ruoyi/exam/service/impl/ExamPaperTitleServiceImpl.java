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
import com.ruoyi.exam.domain.bo.ExamPaperTitleBo;
import com.ruoyi.exam.domain.vo.ExamPaperTitleVo;
import com.ruoyi.exam.domain.ExamPaperTitle;
import com.ruoyi.exam.mapper.ExamPaperTitleMapper;
import com.ruoyi.exam.service.IExamPaperTitleService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 试卷标题Service业务层处理
 *
 * @author zkm
 * @date 2025-03-11
 */
@RequiredArgsConstructor
@Service
public class ExamPaperTitleServiceImpl implements IExamPaperTitleService {

    private final ExamPaperTitleMapper baseMapper;

    /**
     * 查询试卷标题
     */
    @Override
    public ExamPaperTitleVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询试卷标题列表
     */
    @Override
    public TableDataInfo<ExamPaperTitleVo> queryPageList(ExamPaperTitleBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ExamPaperTitle> lqw = buildQueryWrapper(bo);
        Page<ExamPaperTitleVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询试卷标题列表
     */
    @Override
    public List<ExamPaperTitleVo> queryList(ExamPaperTitleBo bo) {
        LambdaQueryWrapper<ExamPaperTitle> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ExamPaperTitle> buildQueryWrapper(ExamPaperTitleBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ExamPaperTitle> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getId() != null, ExamPaperTitle::getId, bo.getId());
        lqw.eq(bo.getPaperId() != null, ExamPaperTitle::getPaperId, bo.getPaperId());
        lqw.like(StringUtils.isNotBlank(bo.getTitleName()), ExamPaperTitle::getTitleName, bo.getTitleName());
        lqw.eq(bo.getOrderNum() != null, ExamPaperTitle::getOrderNum, bo.getOrderNum());
        lqw.eq(bo.getCreateTime() != null, ExamPaperTitle::getCreateTime, bo.getCreateTime());
        return lqw;
    }

    /**
     * 新增试卷标题
     */
    @Override
    public Boolean insertByBo(ExamPaperTitleBo bo) {
        ExamPaperTitle add = BeanUtil.toBean(bo, ExamPaperTitle.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改试卷标题
     */
    @Override
    public Boolean updateByBo(ExamPaperTitleBo bo) {
        ExamPaperTitle update = BeanUtil.toBean(bo, ExamPaperTitle.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ExamPaperTitle entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除试卷标题
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
