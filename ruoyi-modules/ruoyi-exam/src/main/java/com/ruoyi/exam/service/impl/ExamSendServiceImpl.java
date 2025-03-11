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
import com.ruoyi.exam.domain.bo.ExamSendBo;
import com.ruoyi.exam.domain.vo.ExamSendVo;
import com.ruoyi.exam.domain.ExamSend;
import com.ruoyi.exam.mapper.ExamSendMapper;
import com.ruoyi.exam.service.IExamSendService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 试卷发放记录Service业务层处理
 *
 * @author zkm
 * @date  2025-03
 */
@RequiredArgsConstructor
@Service
public class ExamSendServiceImpl implements IExamSendService {

    private final ExamSendMapper baseMapper;

    /**
     * 查询试卷发放记录
     */
    @Override
    public ExamSendVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询试卷发放记录列表
     */
    @Override
    public TableDataInfo<ExamSendVo> queryPageList(ExamSendBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ExamSend> lqw = buildQueryWrapper(bo);
        Page<ExamSendVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询试卷发放记录列表
     */
    @Override
    public List<ExamSendVo> queryList(ExamSendBo bo) {
        LambdaQueryWrapper<ExamSend> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ExamSend> buildQueryWrapper(ExamSendBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ExamSend> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getPaperId() != null, ExamSend::getPaperId, bo.getPaperId());
        lqw.eq(bo.getUserId() != null, ExamSend::getUserId, bo.getUserId());
        lqw.eq(bo.getStartTime() != null, ExamSend::getStartTime, bo.getStartTime());
        lqw.eq(bo.getEndTime() != null, ExamSend::getEndTime, bo.getEndTime());
        lqw.eq(bo.getStatus() != null, ExamSend::getStatus, bo.getStatus());
        lqw.eq(bo.getScore() != null, ExamSend::getScore, bo.getScore());
        lqw.eq(bo.getSubmitTime() != null, ExamSend::getSubmitTime, bo.getSubmitTime());
        return lqw;
    }

    /**
     * 新增试卷发放记录
     */
    @Override
    public Boolean insertByBo(ExamSendBo bo) {
        ExamSend add = BeanUtil.toBean(bo, ExamSend.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改试卷发放记录
     */
    @Override
    public Boolean updateByBo(ExamSendBo bo) {
        ExamSend update = BeanUtil.toBean(bo, ExamSend.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ExamSend entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除试卷发放记录
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
