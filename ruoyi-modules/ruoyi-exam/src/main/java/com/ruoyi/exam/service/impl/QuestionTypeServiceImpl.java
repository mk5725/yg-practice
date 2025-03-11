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
import com.ruoyi.exam.domain.bo.QuestionTypeBo;
import com.ruoyi.exam.domain.vo.QuestionTypeVo;
import com.ruoyi.exam.domain.QuestionType;
import com.ruoyi.exam.mapper.QuestionTypeMapper;
import com.ruoyi.exam.service.IQuestionTypeService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 题目类型Service业务层处理
 *
 * @author zkm
 * @date 2025-03-11
 */
@RequiredArgsConstructor
@Service
public class QuestionTypeServiceImpl implements IQuestionTypeService {

    private final QuestionTypeMapper baseMapper;

    /**
     * 查询题目类型
     */
    @Override
    public QuestionTypeVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询题目类型列表
     */
    @Override
    public TableDataInfo<QuestionTypeVo> queryPageList(QuestionTypeBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<QuestionType> lqw = buildQueryWrapper(bo);
        Page<QuestionTypeVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询全部题目类型列表
     */
    @Override
    public List<QuestionTypeVo> queryListAll(QuestionTypeBo bo) {
        LambdaQueryWrapper<QuestionType> wrapper = buildQueryWrapper(bo);
        return baseMapper.selectVoList(wrapper);
    }

    /**
     * 查询题目类型列表
     */
    @Override
    public List<QuestionTypeVo> queryList(QuestionTypeBo bo) {
        LambdaQueryWrapper<QuestionType> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<QuestionType> buildQueryWrapper(QuestionTypeBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<QuestionType> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getId() != null, QuestionType::getId, bo.getId());
        lqw.eq(bo.getTypeCode() != null, QuestionType::getTypeCode, bo.getTypeCode());
        lqw.like(StringUtils.isNotBlank(bo.getTypeName()), QuestionType::getTypeName, bo.getTypeName());
        return lqw;
    }

    /**
     * 新增题目类型
     */
    @Override
    public Boolean insertByBo(QuestionTypeBo bo) {
        QuestionType add = BeanUtil.toBean(bo, QuestionType.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改题目类型
     */
    @Override
    public Boolean updateByBo(QuestionTypeBo bo) {
        QuestionType update = BeanUtil.toBean(bo, QuestionType.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(QuestionType entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除题目类型
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
